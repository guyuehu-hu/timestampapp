package com.example.timestamp

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timestamp.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
      private lateinit var binding: ActivityMainBinding
      private val fileList = mutableListOf<VideoFile>()

          override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                            binding = ActivityMainBinding.inflate(layoutInflater)
                                    setContentView(binding.root)

                                            setupUI()
          }

              private fun setupUI() {
                        binding.btnPaste.setOnClickListener { pasteFileNames() }
                                binding.btnExport.setOnClickListener { exportToTxt() }
              }

                  private fun pasteFileNames() {
                            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.primaryClip?.getItemAt(0)?.text?.toString()?.let { text ->
                                          fileList.clear()
                                                      text.split("\n").forEach { fileName ->
                                                                        if (fileName.isNotBlank()) {
                                                                                              fileList.add(VideoFile(fileName.trim()))
                                                                        }
                                                      }
                                                                  setupRecyclerView()
                            }
                  }

                      private fun setupRecyclerView() {
                                val adapter = FileAdapter(fileList)
                                        binding.recyclerView.apply {
                                                      layoutManager = LinearLayoutManager(this@MainActivity)
                                                                  this.adapter = adapter
                                        }
                      }

                          private fun exportToTxt() {
                                    val sb = StringBuilder()
                                            fileList.forEach { file ->
                                                          sb.append("${file.fileName},${file.startTime},${file.endTime}\n")
                                            }

                                                    try {
                                                                  val file = File(Environment.getExternalStoragePublicDirectory(
                                                                                    Environment.DIRECTORY_DOCUMENTS), "timestamps.txt")
                                                                              file.writer().use { it.write(sb.toString()) }
                                                                                          Toast.makeText(this, "导出成功：${file.absolutePath}", Toast.LENGTH_LONG).show()
                                                    } catch (e: Exception) {
                                                                  Toast.makeText(this, "导出失败: ${e.message}", Toast.LENGTH_SHORT).show()
                                                    }
                          }
}
