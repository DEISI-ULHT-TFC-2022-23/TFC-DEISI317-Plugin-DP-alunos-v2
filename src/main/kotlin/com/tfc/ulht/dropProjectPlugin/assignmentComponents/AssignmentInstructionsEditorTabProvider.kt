package com.tfc.ulht.dropProjectPlugin.assignmentComponents

import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolder
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.VirtualFileSystem
import com.intellij.testFramework.LightVirtualFile
import com.intellij.ui.components.JBScrollPane
import org.jetbrains.annotations.NotNull
import java.beans.PropertyChangeListener
import java.io.InputStream
import java.io.OutputStream
import javax.swing.JComponent
import javax.swing.JPanel

class AssignmentInstructionsEditorTabProvider : FileEditorProvider, DumbAware {
    override fun accept(project: @NotNull Project, file: @NotNull VirtualFile): Boolean {
        return file is InstructionsVirtualFile
    }

    override fun createEditor(project: @NotNull Project, file: @NotNull VirtualFile): FileEditor {
        return InstructionsPanelEditor(file as InstructionsVirtualFile)
    }

    override fun getEditorTypeId(): String {
        return "assignment-details-editor"
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
    }
}

class InstructionsPanelEditor(private val instructionsVirtualFile: InstructionsVirtualFile) :
    FileEditor, UserDataHolder {

    private var myPanel = JPanel()
    private val userData = UserDataHolderBase()

    init {

        myPanel = instructionsVirtualFile.editorPanel
    }

    override fun getFile(): VirtualFile {
        return instructionsVirtualFile
    }
    override fun <T : Any?> getUserData(key: Key<T>): T? {
        return userData.getUserData(key)
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        userData.putUserData(key,value)
    }

    override fun dispose() {
        //perform any cleanup when the editor is closed
    }

    override fun getComponent(): JComponent {
        return myPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return myPanel
    }

    override fun getName(): String {
        return instructionsVirtualFile.name
    }

    override fun getState(level: FileEditorStateLevel): FileEditorState {
        return FileEditorState.INSTANCE
    }

    override fun setState(state: FileEditorState) {
        //save the state of the editor if necessary
    }

    override fun isModified(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        //add property change listener if necessary
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        //remove a property change listener if necessary
    }

    override fun getCurrentLocation(): FileEditorLocation? {
        return null
    }

}

class InstructionsVirtualFile(private val myName : String, val editorPanel : JPanel) : VirtualFile() {
    override fun getName(): String {
        return myName
    }

    override fun getFileSystem(): VirtualFileSystem {
        return InstructionsFileSystem.INSTANCE
    }

    override fun getPath(): String {
        return "/$myName"
    }

    override fun isWritable(): Boolean {
        return false
    }

    override fun isDirectory(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun getParent(): VirtualFile? {
        return null
    }

    override fun getChildren(): Array<VirtualFile>? {
        return null
    }

    override fun getOutputStream(requestor: Any?, newModificationStamp: Long, newTimeStamp: Long): OutputStream {
        throw UnsupportedOperationException()
    }

    override fun contentsToByteArray(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun getTimeStamp(): Long {
        return 0
    }

    override fun getLength(): Long {
        return 0
    }

    override fun refresh(asynchronous: Boolean, recursive: Boolean, postRunnable: Runnable?) {
        //refresh the virtual file if necessary
    }

    override fun getInputStream(): InputStream {
        throw UnsupportedOperationException()
    }

}

object InstructionsFileSystem : VirtualFileSystem() {

    val INSTANCE = this
    override fun getProtocol(): String {
        return "assignment-details"
    }

    override fun findFileByPath(path: String): VirtualFile? {
        return null
    }

    override fun refresh(asynchronous: Boolean) {
        TODO("Not yet implemented")
    }

    override fun refreshAndFindFileByPath(path: String): VirtualFile? {
        return null
    }

    override fun addVirtualFileListener(listener: VirtualFileListener) {
        TODO("Not yet implemented")
    }

    override fun removeVirtualFileListener(listener: VirtualFileListener) {
        TODO("Not yet implemented")
    }

    override fun deleteFile(requestor: Any?, vFile: VirtualFile) {
        TODO("Not yet implemented")
    }

    override fun moveFile(requestor: Any?, vFile: VirtualFile, newParent: VirtualFile) {
        TODO("Not yet implemented")
    }

    override fun renameFile(requestor: Any?, vFile: VirtualFile, newName: String) {
        TODO("Not yet implemented")
    }

    override fun createChildFile(requestor: Any?, vDir: VirtualFile, fileName: String): VirtualFile {
        return LightVirtualFile("buildCopy.txt", PlainTextLanguage.INSTANCE,"cant copy")
    }

    override fun createChildDirectory(requestor: Any?, vDir: VirtualFile, dirName: String): VirtualFile {
        return LightVirtualFile("buildCopy.txt",PlainTextLanguage.INSTANCE,"cant copy")
    }

    override fun copyFile(
        requestor: Any?,
        virtualFile: VirtualFile,
        newParent: VirtualFile,
        copyName: String
    ): VirtualFile {
        return LightVirtualFile("buildCopy.txt",PlainTextLanguage.INSTANCE,"cant copy")
    }

    override fun isReadOnly(): Boolean {
        return true
    }

}
