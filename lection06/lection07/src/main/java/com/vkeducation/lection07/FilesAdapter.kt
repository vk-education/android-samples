package com.vkeducation.lection07

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vkeducation.lection07.FilesAdapter.FileViewHolder
import java.io.File
import java.util.*

/**
 * Адаптер для [RecyclerView], отображающий список файлов в указанной директории.
 * Начинает обзор с корня внешнего хранилища. Поддерживает навигацию по директориям.
 * Для упрощения кода опущены проверки наличия (примонтированности) внешнего хранилища.
 */
internal class FilesAdapter(context: Context?) : RecyclerView.Adapter<FileViewHolder>() {
    private val layoutInflater: LayoutInflater
    private var initialFile: File? = null
    private var files: Array<File>? = null
    private var currentFile: File? = null
    fun init() {
        initialFile = Environment.getExternalStorageDirectory()
        setDirectory(initialFile)
    }

    private fun setDirectory(file: File?) {
        currentFile = file
        files = file!!.listFiles()
        sortFiles(files)
        notifyDataSetChanged()
    }

    fun goBack(): Boolean {
        if (currentFile == initialFile) {
            return false
        }
        val parent = currentFile!!.parentFile
        if (parent != null) {
            setDirectory(parent)
            return true
        }
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(layoutInflater.inflate(R.layout.file_item, parent, false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(files!![position])
    }

    override fun getItemCount(): Int {
        return if (files != null) files!!.size else 0
    }

    internal inner class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val fileName: TextView
        @SuppressLint("SetTextI18n")
        fun bind(file: File) {
            if (file.isDirectory) {
                fileName.setTypeface(null, Typeface.BOLD)
                fileName.text = "[" + file.name + "]"
            } else {
                fileName.setTypeface(null, Typeface.NORMAL)
                fileName.text = file.name
            }
            itemView.setOnClickListener {
                if (file.isDirectory) {
                    setDirectory(file)
                } else {
                    try {
                        val i = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.fromFile(file)
                        }
                        fileName.context.startActivity(i)
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(fileName.context, "no activity :( ",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        init {
            fileName = view.findViewById(R.id.filename)
        }
    }

    companion object {
        private fun sortFiles(files: Array<File>?) {
            if (files == null) {
                return
            }
            Arrays.sort(files, Comparator { f1, f2 ->
                if (f1.isDirectory && !f2.isDirectory) return@Comparator -1
                if (f2.isDirectory && !f1.isDirectory) 1 else f1.name.compareTo(f2.name)
            })
        }
    }

    init {
        layoutInflater = LayoutInflater.from(context)
        init()
    }
}