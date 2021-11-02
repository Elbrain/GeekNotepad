package uk.org.websolution.trader_org_tool.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uk.org.websolution.trader_org_tool.R
import java.util.*

class ListOfNotesFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var addNoteFab: FloatingActionButton? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_notes, container, false)
        recyclerView = view.findViewById(R.id.recycler_list_of_notes)
        addNoteFab = view.findViewById(R.id.fab_add_note)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateList()
        initFab()
    }

    private fun generateList() {
        val adapter = NotesAdapter()
        if (arguments != null) {
            val allNotes: ArrayList<NoteEntity> = requireArguments().getParcelableArrayList(ARG_NOTES)!!
            adapter.setData(allNotes)
        }
        adapter.setOnItemClickListener(object : NotesAdapter.OnItemClickListener {
            override fun onItemClick(note: NoteEntity) {
                val controller = activity as ShowNoteController?
                controller?.showNote(note)
            }

            override fun onEditClicked(note: NoteEntity) {
                val controller = activity as EditNoteController?
                if (controller != null) {
                    controller.editNote(note)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onDeleteClicked(note: NoteEntity) {
                val controller = activity as DeleteNoteController?
                if (controller != null) {
                    controller.deleteNote(note)
                    adapter.notifyDataSetChanged()
                }
            }
        })
        recyclerView!!.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView!!.adapter = adapter
    }

    private fun initFab() {
        addNoteFab!!.setOnClickListener { v: View? ->
            val addNoteFragment = AddNoteFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, addNoteFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    interface ShowNoteController {
        fun showNote(note: NoteEntity?)
    }

    interface DeleteNoteController {
        fun deleteNote(note: NoteEntity?)
    }

    interface EditNoteController {
        fun editNote(note: NoteEntity?)
    }

    companion object {
        private const val ARG_NOTES = "ARG_NOTES"
        @JvmStatic
        fun newInstance(notesList: ArrayList<NoteEntity?>?): ListOfNotesFragment {
            val fragment = ListOfNotesFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_NOTES, notesList)
            fragment.arguments = args
            return fragment
        }
    }
}