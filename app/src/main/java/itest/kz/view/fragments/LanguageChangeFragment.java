package itest.kz.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import itest.kz.R;
import itest.kz.databinding.FragmentLanguageChangeBinding;

import itest.kz.util.AppUtils;
import itest.kz.util.Constant;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainHomeActivity;
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

    private TextView dialogTextLang;
    private Button buttonYesLang;
    private Button buttonNoLang;
    private boolean noChangedLang = false;

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

//        ((MainHomeActivity)getActivity()).setNavigationVisibility(false);


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
                                    if (!noChangedLang) {
                                        kazRadioBurtton.setChecked(false);
                                        selectedLanguage = "ru";
                                        showDialogChangeLang(selectedLanguage);
                                    }
                                    else
                                        noChangedLang = false;
//                                    SharedPreferences.Editor editor = settings.edit();
//                                    editor.clear();
//                                    editor.putString(Constant.LANG, selectedLanguage);
//                                    editor.apply();
//                                    editor.commit();
//                                    AppUtils.setLocale((selectedLanguage.equals(Constant.KZ)?"en":"ru")
//                                            ,getContext());

                                }
                                break;
                            case R.id.kaz_radio:
                                if (isChecked)
                                {
                                    if (!noChangedLang) {
                                        rusRadioButton.setChecked(false);
                                        selectedLanguage = "kz";
                                        showDialogChangeLang(selectedLanguage);
                                    }
                                    else
                                        noChangedLang =false;
//                                    SharedPreferences.Editor editor = settings.edit();
//                                    editor.clear();
//                                    editor.putString(Constant.LANG, selectedLanguage);
//                                    editor.apply();
//                                    editor.commit();
//                                    AppUtils.setLocale((selectedLanguage.equals(Constant.KZ)?"en":"ru")
//                                            ,getContext());

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
//                if (selectedLanguage != null)
//                {
//                    SharedPreferences.Editor editor = settings.edit();
//                    editor.clear();
//                    editor.putString(Constant.LANG, selectedLanguage);
//                    editor.apply();
//                    editor.commit();
//                    AppUtils.setLocale((selectedLanguage.equals(Constant.KZ)?"en":"ru")
//                            ,getContext());
//
//                }
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

    public void showDialogChangeLang(String selectedLanguage)
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(getContext());
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog)
//            {
//                openAuthActivity();
////                    finishTest(testIdMain);
//                //System.out.println(testIdMain);//103080954
////                dialog.dismiss();
//            }
//        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTextLang = dialog.findViewById(R.id.dialog_text);
        buttonYesLang = dialog.findViewById(R.id.buttonOk);
        buttonNoLang = dialog.findViewById(R.id.buttonCancel);
//        buttonNoAuth.setVisibility(View.GONE);
        buttonYesLang.setText(R.string.yes);
        buttonNoLang.setText(R.string.no);
        dialogTextLang.setText(R.string.changeLang);
        buttonYesLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYesLang.setEnabled(false);
                buttonNoLang.setEnabled(false);
                dialog.dismiss();
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.putString(Constant.LANG, selectedLanguage);
                editor.apply();
                editor.commit();
                AppUtils.setLocale((selectedLanguage.equals(Constant.KZ)?"en":"ru")
                        ,getContext());
                refleshActivity();
//                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954

            }
        });

        buttonNoLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (selectedLanguage.equals(Constant.RU))
                {
                    noChangedLang = true;
                    rusRadioButton.setChecked(false);
                    kazRadioBurtton.setChecked(true);

                }
                else
                {
                    noChangedLang = true;
                    rusRadioButton.setChecked(true);
                    kazRadioBurtton.setChecked(false);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void refleshActivity()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity) getContext()).finish();
//                    ((Activity)getContext()).overridePendingTransition( 0, 0);
                ((Activity) getContext()).startActivity(((Activity) getContext()).getIntent());
//                    ((Activity)getContext()).overridePendingTransition( 0, 0);
            }
        });
        thread.start();
    }
}
