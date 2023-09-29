package com.iondodon.gofilesorganiser

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager

class GoFileGroupNode(
    project: Project,
    private val baseFile: VirtualFile,
    private val prefixedFiles: List<VirtualFile>,
    private val settings: ViewSettings?  // store settings as a field
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

        // Get the default icon for Go files
        val goFileType = FileTypeManager.getInstance().getFileTypeByExtension("go")
        data.setIcon(goFileType.icon)
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
