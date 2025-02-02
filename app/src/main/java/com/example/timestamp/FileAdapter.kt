package com.example.timestamp

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileAdapter(private val items: List<VideoFile>) : 
    RecyclerView.Adapter<FileAdapter.ViewHolder>() {

          inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                    val tvFileName: TextView = view.findViewById(R.id.tvFileName)
                            val etStartHour: EditText = view.findViewById(R.id.etStartHour)
                                    val etStartMin: EditText = view.findViewById(R.id.etStartMin)
                                            val etStartSec: EditText = view.findViewById(R.id.etStartSec)
                                                    val etEndHour: EditText = view.findViewById(R.id.etEndHour)
                                                            val etEndMin: EditText = view.findViewById(R.id.etEndMin)
                                                                    val etEndSec: EditText = view.findViewById(R.id.etEndSec)
          }

              override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                        val view = LayoutInflater.from(parent.context)
                                    .inflate(R.layout.item_file_input, parent, false)
                                            return ViewHolder(view).apply {
                                                          setupAutoJump(etStartHour, etStartMin)
                                                                      setupAutoJump(etStartMin, etStartSec)
                                                                                  setupAutoJump(etStartSec, etEndHour)
                                                                                              setupAutoJump(etEndHour, etEndMin)
                                                                                                          setupAutoJump(etEndMin, etEndSec)
                                            }
              }

                  private fun setupAutoJump(current: EditText, next: EditText) {
                            current.addTextChangedListener(object : TextWatcher {
                                          override fun afterTextChanged(s: Editable?) {
                                                            if (s?.length == 2) {
                                                                                  next.requestFocus()
                                                                                                      if (s.toString().toInt() < 10) {
                                                                                                                                current.setText("0${s}")
                                                                                                      }
                                                            }
                                          }
                                                      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                                                  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                            })
                  }

                      override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                                val file = items[position]
                                holder.tvFileName.text = file.fileName

                                file.startTime.split(":").let {
                                              holder.etStartHour.setText(it.getOrElse(0) { "00" })
                                                          holder.etStartMin.setText(it.getOrElse(1) { "00" })
                                                                      holder.etStartSec.setText(it.getOrElse(2) { "00" })
                                }

                                                file.endTime.split(":").let {
                                                              holder.etEndHour.setText(it.getOrElse(0) { "00" })
                                                                          holder.etEndMin.setText(it.getOrElse(1) { "00" })
                                                                                      holder.etEndSec.setText(it.getOrElse(2) { "00" })
                                                }
                      }

                          override fun getItemCount() = items.size
    }
