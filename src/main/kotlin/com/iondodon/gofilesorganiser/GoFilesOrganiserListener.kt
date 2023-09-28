package com.iondodon.gofilesorganiser

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.project.Project


class GoFilesOrganiserListener(private val project: Project) {
    fun handleGoFileChange(file: VirtualFile) {
        // Here, you might decide to trigger a refresh of the Project View, if necessary.
    }
}