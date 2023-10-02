package com.iondodon.gofilesorganiser

import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

class GoFileGroupNode(
    project: Project,
    baseFile: PsiFile,
    private val prefixedFiles: List<VirtualFile>,
    private val settings: ViewSettings?
) : PsiFileNode(project, baseFile, settings) {

    override fun getChildrenImpl(): Collection<AbstractTreeNode<*>> {
        val psiManager = PsiManager.getInstance(project)
        return prefixedFiles.mapNotNull { virtualFile ->
            psiManager.findFile(virtualFile)?.let { psiFile ->
                PsiFileNode(project, psiFile, settings)
            }
        }
    }

}
