package itest.kz.view.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentCertificationBinding;


import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.viewmodel.CertificationFragmentViewModel;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class CertificationFragment extends Fragment implements Observer
{
    public CertificationFragmentViewModel certificationFragmentViewModel;
    public FragmentCertificationBinding fragmentCertificationBinding;
    private Toolbar myToolbar;
    private ImageButton titleButton;
    private ImageButton titleButtonClose;
    private ImageButton buttonYes;
    private ImageButton buttonNo;
    private TextView dialogText;
    private String language;
    private SharedPreferences settings;
    private SubjectAdapter subjectAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentCertificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_certification, container, false);
        certificationFragmentViewModel = new CertificationFragmentViewModel(getContext());
        fragmentCertificationBinding.setCertification(certificationFragmentViewModel);
        settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        setUpListOfSbjectsView(fragmentCertificationBinding.listSubject);
        setUpObserver(certificationFragmentViewModel);
//        myToolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
//        titleButton = (ImageButton) getActivity().findViewById(R.id.imageButtonTitle);
//        titleButtonClose = (ImageButton) getActivity().findViewById(R.id.buttonFinishTest);

//        navigationToolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
//        numberPager = (TextView) getActivity().findViewById(R.id.text_number_pager);
//        if (currentPosition == )
//        int temp = 0;
//
//        for (int i = 0; i < tests.getQuestions().size(); i++)
//        {
//            if (tests.getQuestions().get(i) == test) {
//                temp = --i;
//                break;
//            }
//        }
//        numberPager.setText(currentPosition + "/" + tests.getQuestions().size());



//        titleButtonClose.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//
//
////                if (resultTag == null)
////                {
//                    Dialog dialog = new Dialog(getContext());
//
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.dialog);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialogText = dialog.findViewById(R.id.dialog_text);
//                    if (language.equals(Constant.KZ))
//                        dialogText.setText(R.string.textDialogCertificationKz);
//                    else
//                        dialogText.setText(R.string.textDialogCertificationRu);
//                    buttonYes = dialog.findViewById(R.id.buttonOk);
//                    buttonNo = dialog.findViewById(R.id.buttonCancel);
//                    buttonYes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////                            finishTest(testIdMain);
//
//
//                            //System.out.println(testIdMain);//103080954
//
//                        }
//                    });
//
//                    buttonNo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                }
////                else
////                {
////                    getActivity().finish();
////                }
////            }
//        });

        return fragmentCertificationBinding.getRoot();
    }


    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof  CertificationFragmentViewModel) {
            subjectAdapter = (SubjectAdapter) fragmentCertificationBinding.listSubject
                    .getAdapter();
            CertificationFragmentViewModel certificationFragmentViewModel
                    = (CertificationFragmentViewModel) o;
            subjectAdapter.setSubjectList(certificationFragmentViewModel
                    .getSubjectList());
        }
    }



    // set up the list of user with recycler view
    private void setUpListOfSbjectsView(RecyclerView listSubject) {
        SubjectAdapter subjectAdapter = new SubjectAdapter();
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), HORIZONTAL);
//        itemDecor
        listSubject.addItemDecoration(itemDecor);
        listSubject.setAdapter(subjectAdapter);
        listSubject.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectAdapter.setOnItemListener(
                new SubjectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Subject item, int i) throws CloneNotSupportedException
                    {
                        if (item.isExpand())
                            item.setExpand(false);
                        else
                            item.setExpand(true);
                        subjectAdapter.notifyItemChanged(i);
                    }
                }
//        new SubjectAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Subject item) throws CloneNotSupportedException
//            {
////                int pos = listSubject.indexOfChild(view);
//
//                changePricesInTheList(item);
////                view.getRootView().findViewById()
////                System.out.println("pos");
////                System.out.println(pos);
//            }
//            @Override
//            public void onItemClick(Subject item) throws CloneNotSupportedException
//            {
//                int pos = listSubject.indexOfChild(item);
//                System.out.println("OnItem Click");
//                subjectAdapter.getItemId(position);
//                item.setExpand(true);


//                changePricesInTheList(item);
//
//                RecyclerView.ViewHolder view = subjectAdapter.getSubjectAdapterViewHolder();


//                System.out.println("pos");
//                System.out.println(subjectAdapter.getSubjectAdapterViewHolder().getAdapterPosition());
//                RecyclerView.ViewHolder view = listSubject.findViewHolderForLayoutPosition
//                        (subjectAdapter.getSubjectAdapterViewHolder().getLayoutPosition());
//                CardView cardView =  view.itemView.findViewById(R.id.expand_cardview);
//                cardView.setVisibility(View.VISIBLE);



//                TextView textView = view.itemView.findViewById(R.id.textview1);
//                cardView
//                        .setCardBackgroundColor(
////                                        Color.parseColor("#ff2daafc")
//                                Color.WHITE
//                        );
//                textView.setTextColor(Color.BLACK);
//
//                buttonsVisible.set(View.VISIBLE);
//                textColor.set(Color.parseColor("#D8D8D8"));
//                isClicked = true;
//            colorIcon.set(Color.parseColor("#36ABF9"));
//            Drawable mDrawable = context.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp);
//            mDrawable.setColorFilter(new
//                    PorterDuffColorFilter(0x36ABF9,PorterDuff.Mode.MULTIPLY));
//            }
//        }
        );
    }

    private void changePricesInTheList(Subject item) throws CloneNotSupportedException {

        List<Subject> subjectList = subjectAdapter.getSubjectList();
        ArrayList<Subject> models = new ArrayList<>();
//        int previousPosition = 0;
//        int nextPosition;

//        subjectAdapter.setSubjectList(certificationFragmentViewModel
//                .getSubjectList());

        Integer position = null;
        for (Subject model : subjectList) {
            models.add(model.clone());
        }

        for (int i = 0; i < models.size(); i++)
        {
            if (models.get(i).isExpand() && models.get(i).getId() != item.getId())
            {
                models.get(i).setExpand(false);
            }
            if (models.get(i).getId() == item.getId())
                position = i;
        }
        if (position != null)
        {
            if (!models.get(position).isExpand())
                models.get(position).setExpand(true);
            else
                models.get(position).setExpand(false);
        }


//        System.out.println(models);



        subjectAdapter.setSubjectList(models);
//        setData(models);
    }

    public void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        certificationFragmentViewModel.reset();
    }
}
