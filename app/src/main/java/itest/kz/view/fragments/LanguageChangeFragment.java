package itest.kz.view.fragments;

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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import itest.kz.R;
import itest.kz.databinding.FragmentLanguageChangeBinding;

import itest.kz.util.Constant;
import itest.kz.view.activity.HomeActivity;
import itest.kz.viewmodel.LanguageChangeFragmentViewModel;

import static android.content.Context.MODE_PRIVATE;

public class LanguageChangeFragment extends Fragment
{
    private FragmentLanguageChangeBinding fragmentLanguageChangeBinding;
    private LanguageChangeFragmentViewModel languageChangeFragmentViewModel;
    private RadioButton kazRadioBurtton;
    private RadioButton rusRadioButton;
    private RadioGroup rusKazRadiouGroup;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String language;
    private String selectedLanguage;
    private SharedPreferences settings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        fragmentLanguageChangeBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_language_change, container, false);
        languageChangeFragmentViewModel = new LanguageChangeFragmentViewModel(getContext());
        fragmentLanguageChangeBinding.setLang(languageChangeFragmentViewModel);

        ((HomeActivity)getActivity()).setNavigationVisibility(false);

        kazRadioBurtton = fragmentLanguageChangeBinding.kazRadio;
        rusRadioButton = fragmentLanguageChangeBinding.rusRadio;

        setMyToolbar();
        if (language.equals(Constant.KZ))
            kazRadioBurtton.setChecked(true);
        else
            rusRadioButton.setChecked(true);




        CompoundButton.OnCheckedChangeListener onCheckedChangeListener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        switch (buttonView.getId())
                        {
                            case R.id.rus_radio:
                                if (isChecked)
                                {
                                    kazRadioBurtton.setChecked(false);
                                    selectedLanguage = "ru";
                                }
                                break;
                            case R.id.kaz_radio:
                                if (isChecked)
                                {
                                    rusRadioButton.setChecked(false);
                                    selectedLanguage = "kz";

                                }
                                break;
                            default:
                                break;
                        }
                    }
                };
        kazRadioBurtton.setOnCheckedChangeListener(onCheckedChangeListener);
        rusRadioButton.setOnCheckedChangeListener(onCheckedChangeListener);





        return fragmentLanguageChangeBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                if (selectedLanguage != null)
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.clear();
                    editor.putString(Constant.LANG, selectedLanguage);
                    editor.apply();
                    editor.commit();
                }


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
        myToolbar = (Toolbar) fragmentLanguageChangeBinding
                .toolbarLanguage;
        mainToolbarText = (TextView) fragmentLanguageChangeBinding
                .toolbarTitle;
        mainToolbarText.setText("Тіл");
        mainToolbarText.setTextColor(Color.WHITE);
        myToolbar.setTitle("");

        ((AppCompatActivity)getActivity())
                .setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity())
                .getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
