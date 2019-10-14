package itest.kz.view.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import itest.kz.R;
import itest.kz.databinding.FragmentAgreementBinding;
import itest.kz.util.Constant;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.viewmodel.AgreementViewModel;

import static android.content.Context.MODE_PRIVATE;

public class AgreementFragment extends Fragment
{
    private FragmentAgreementBinding fragmentAgreementBinding;
    private AgreementViewModel agreementViewModel;
    private String language;
    private SharedPreferences settings;
    private Toolbar myToolbar;
    private TextView mainToolbarText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        fragmentAgreementBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_agreement,
                        container, false);
        agreementViewModel = new AgreementViewModel(getContext());
//        ((HomeActivity)getActivity()).setNavigationVisibility(false);
        fragmentAgreementBinding.setAgreement(agreementViewModel);
        setMyToolbar();
        return fragmentAgreementBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                ((MainHomeActivity)(Activity)getContext()).setNavigationVisibiltity(true);
                closefragment();
                return true;
//            case R.id.menu_save:
//                System.out.println("save");
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closefragment()
    {
        getFragmentManager().popBackStackImmediate();
    }

    private void setMyToolbar()
    {
        //        myToolbar = (Toolbar) fragmentProfileBinding.getRoot().findViewById(R.id.toolbar_profile);
        myToolbar = (Toolbar) fragmentAgreementBinding
                .toolbarLanguage;
        mainToolbarText = (TextView) fragmentAgreementBinding
                .toolbarTitle;
//        mainToolbarText.setText("Тіл");
        mainToolbarText.setTextColor(Color.WHITE);
        myToolbar.setTitle("");

        ((AppCompatActivity)getActivity())
                .setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity())
                .getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
