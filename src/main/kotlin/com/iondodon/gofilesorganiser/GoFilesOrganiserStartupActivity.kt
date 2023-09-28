package com.iondodon.gofilesorganiser

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.intellij.openapi.vfs.newvfs.BulkFileListener

class GoFilesOrganiserStartupActivity : StartupActivity {
    override fun runActivity(project: Project) {
        val connection = project.messageBus.connect()
        connection.subscribe(VirtualFileManager.VFS_CHANGES, object : BulkFileListener {
            override fun after(events: List<VFileEvent>) {
                val organiser = GoFilesOrganiserListener(project)
                for (event in events) {
                    val file = event.file
                    if (file != null && "go" == file.extension) {
                        organiser.handleGoFileChange(file)
                    }
                }
            }
        })
    }
}