package itest.kz.view.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import itest.kz.R;

public class ArrayListFragment extends ListFragment
{
    int fragNum;
    String question;
    String arr[] = { "This is", "a Truiton", "Demo", "App", "For", "Showing",
            "FragmentStatePagerAdapter", "and ViewPager", "Implementation" };

    public static ArrayListFragment init(int val) {
        ArrayListFragment truitonList = new ArrayListFragment();

        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
//        args.putString("question", question);
        truitonList.setArguments(args);


        return truitonList;
    }

    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
//        question = getArguments().getString("question");
    }

    /**
     * The Fragment's UI is a simple text view showing its instance number and
     * an associated list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_pager_list,
                container, false);
        View tv = layoutView.findViewById(R.id.text);
        ((TextView) tv).setText("Truiton Fragment #" + question);
        return layoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, arr));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("Truiton FragmentList", "Item clicked: " + id);
    }
}
