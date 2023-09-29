package com.iondodon.gofilesorganiser

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager

class GoFileGroupingProvider : TreeStructureProvider {

    override fun modify(
        parent: AbstractTreeNode<*>,
        children: Collection<AbstractTreeNode<*>>,
        settings: ViewSettings?
    ): Collection<AbstractTreeNode<*>> {
        val result = mutableListOf<AbstractTreeNode<*>>()

        val goFileNodes = children.filterIsInstance<PsiFileNode>().filter {
            it.virtualFile?.extension == "go"
        }

        val handledFiles = mutableSetOf<VirtualFile>()

        goFileNodes.forEach { node ->
            val prefixFile = node.virtualFile ?: return@forEach
            if (handledFiles.contains(prefixFile)) return@forEach

            val prefixedFiles = goFileNodes.filter {
                val name = it.virtualFile?.nameWithoutExtension ?: return@filter false
                name != prefixFile.nameWithoutExtension && name.startsWith(prefixFile.nameWithoutExtension)
            }.map { it.virtualFile }.filterNotNull()

            if (prefixedFiles.isNotEmpty()) {
                result.add(GoFileGroupNode(node.project, prefixFile, prefixedFiles, settings))
                handledFiles.addAll(prefixedFiles)
                handledFiles.add(prefixFile)
            } else {
                result.add(node)
            }
        }

        // Add all other nodes (folders and other file types) to the result list
        result.addAll(children.filterNot { it in goFileNodes })

        return result
    }
}



class GoFileGroupNode(
    project: Project,
    val baseFile: VirtualFile,
    val prefixedFiles: List<VirtualFile>,
    val settings: ViewSettings?  // store settings as a field
) : AbstractTreeNode<VirtualFile>(project, baseFile) {

    override fun getChildren(): Collection<AbstractTreeNode<*>> {
        val psiManager = PsiManager.getInstance(project)
        return prefixedFiles.mapNotNull { virtualFile ->
            psiManager.findFile(virtualFile)?.let { psiFile ->
                PsiFileNode(project, psiFile, settings)
            }
        }
    }

    override fun update(data: PresentationData) {
        data.presentableText = baseFile.name
        // Set your desired icon here
    }

    override fun navigate(requestFocus: Boolean) {
        OpenFileDescriptor(project, baseFile).navigate(requestFocus)
    }

    override fun canNavigate(): Boolean {
        return true
    }

    override fun canNavigateToSource(): Boolean {
        return true
    }

    override fun getVirtualFile(): VirtualFile? {
        return baseFile
    }

    override fun getName(): String {
        return baseFile.name
    }

    // All other overridden methods with default or example implementations

    override fun hasProblemFileBeneath(): Boolean {
        return false
    }

    override fun isAlwaysLeaf(): Boolean {
        return false
    }

    override fun isAlwaysShowPlus(): Boolean {
        return true  // Set to true since x.go is expandable
    }

    override fun getFileStatus(): FileStatus {
        return FileStatus.NOT_CHANGED
    }

    override fun getWeight(): Int {
        return 0
    }

    // ... any other methods you need to override

}
