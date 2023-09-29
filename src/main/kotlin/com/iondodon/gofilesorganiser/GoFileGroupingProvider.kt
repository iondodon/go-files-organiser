package com.iondodon.gofilesorganiser

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.vfs.VirtualFile

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
            }.mapNotNull { it.virtualFile }

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
