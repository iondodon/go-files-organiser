package com.iondodon.gofilesorganiser

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode

class CustomTreeStructureProvider : TreeStructureProvider {

    override fun modify(
        parent: AbstractTreeNode<*>,
        children: Collection<AbstractTreeNode<*>>,
        settings: ViewSettings?
    ): Collection<AbstractTreeNode<*>> {
        val modified = mutableListOf<AbstractTreeNode<*>>()
        val goFiles = mutableListOf<PsiFileNode>()

        for (child in children) {
            if (child is PsiFileNode) {
                val virtualFile = child.virtualFile
                if (virtualFile?.extension == "go") {
                    goFiles.add(child)
                    continue
                }
            }
            modified.add(child)
        }

        // Custom logic to nest xy.go files under x.go
        // For simplicity, we just add goFiles as they are. Your nesting logic will be implemented here.
        modified.addAll(goFiles)

        return modified
    }
}
