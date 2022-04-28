package ru.mail.techpark.lesson9

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
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import ru.mail.techpark.lesson9.FilesAdapter.FileViewHolder
import java.io.File
import java.util.*

/**
 * Адаптер для [RecyclerView], отображающий список файлов в указанной директории.
 * Начинает обзор с корня внешнего хранилища. Поддерживает навигацию по директориям.
 * Для упрощения кода опущены проверки наличия (примонтированности) внешнего хранилища.
 */
internal class FilesAdapter(private val context: Context?) : RecyclerView.Adapter<FileViewHolder>() {
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
        private val mFileName: TextView
        @SuppressLint("SetTextI18n")
        fun bind(file: File) {
            if (file.isDirectory) {
                mFileName.setTypeface(null, Typeface.BOLD)
                mFileName.text = "[" + file.name + "]"
            } else {
                mFileName.setTypeface(null, Typeface.NORMAL)
                mFileName.text = file.name
            }
            itemView.setOnClickListener {
                if (file.isDirectory) {
                    setDirectory(file)
                } else {
                    try {
                        context!!
                        val uri = FileProvider.getUriForFile(
                            context,
                            context.applicationContext.packageName + ".provider",
                            file
                        )
                        val i = Intent(Intent.ACTION_VIEW).apply {
//                            data = Uri.fromFile(file) // Crashes in Pre-Android N ! Use File Provider
                            data = uri
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                        mFileName.context.startActivity(i)
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(mFileName.context, "no activity :( ",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        init {
            mFileName = view.findViewById(R.id.filename)
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