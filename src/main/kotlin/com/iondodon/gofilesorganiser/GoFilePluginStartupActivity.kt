package com.iondodon.gofilesorganiser

import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.tree.TreePath

class GoFilePluginStartupActivity : StartupActivity {

    override fun runActivity(project: Project) {
        val projectView = ProjectView.getInstance(project)
        val tree = projectView.currentProjectViewPane.tree

        tree.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {  // Detecting double-click
                    val path: TreePath = tree.getPathForLocation(e.x, e.y) ?: return
                    val lastPathComponent = path.lastPathComponent
                    if (lastPathComponent is GoFileGroupNode) {
                        lastPathComponent.navigate(true)
                        e.consume()  // Consume the event to prevent default double-click behavior
                    }
                }
            }
        })
    }
}