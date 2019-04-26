package itest.kz.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function5;
import itest.kz.R;
import itest.kz.model.Profile;
import itest.kz.util.BindableFieldTarget;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.util.InputValidator;
import itest.kz.util.RxUtils;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

public class ProfileInfoViewModel extends BaseObservable
{
    private Context context;
    private BindableFieldTarget bindableFieldTarget;
    private Profile profile;
    private ObservableField<Drawable> profileImage;
    private Action clickOnPhoto;
    public Action clickOnSave;
    public ObservableField<String> name;
    public ObservableField<String> surname;
    public ObservableField<String> date;
    public ObservableField<String> login;
    public ObservableField<String> email;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String language;
    public ObservableField<Boolean> enableSave;
    private static final int REQUEST_GALLERY_CODE = 200;
    public ObservableInt progress = new ObservableInt(View.GONE);

    public ObservableInt getProgress()
    {
        return progress;
    }


    public void setProgress(boolean isProgress, Profile profile)
    {
        if (isProgress)
        {
            progress.set(View.VISIBLE);
            setProfile(profile);
        }
        else
        {
            progress.set(View.GONE);
        }

//        notifyChange();
    }

    public void setProgress(boolean isProgress)
    {
        if (isProgress)
            progress.set(View.VISIBLE);
        else
        {
            progress.set(View.GONE);
        }

//        notifyChange();
    }

    public int getHintEmailText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintEmailKz;
        return R.string.hintEmailRu;
    }

    public int getHintLoginText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintLoginKz;
        return R.string.hintLoginRu;
    }

    public int getLoginEmailHeader()
    {
        if (language.equals(Constant.KZ))
            return R.string.loginEmailHeaderKz;
        return R.string.loginEmailHeaderRu;
    }

    public int getHintDateOfBirthText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintDateOfBirthKz;
        return R.string.hintDateOfBirthRu;
    }

    public int getDateOfBirthText()
    {
        if (language.equals(Constant.KZ))
            return R.string.dateOfBirthKz;
        return R.string.dateOfBirthRu;
    }

    public int getHintSurnameText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintSurnameKz;
        return R.string.hintSurnameRu;
    }
    public int getSurnameText()
    {

        if (language.equals(Constant.KZ))
            return R.string.surnameKz;
        return R.string.surnameKz;
    }

    public int getHintNameText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintNameKz;
        return R.string.hintNameRu;
    }

    public int getNameText()
    {
        if (language.equals(Constant.KZ))
            return R.string.nameKz;
        return R.string.nameRu;
    }



    public int getTitleText()
    {
        if (language.equals(Constant.KZ))
            return R.string.profileInfoKz;
        return R.string.profileInfoRu;
    }

    public int getSaveText()
    {
        if (language.equals(Constant.KZ))
            return R.string.saveTitleKz;
        return R.string.saveTitleRu;
    }

    public ProfileInfoViewModel( Context context, Profile profile)
    {
        this.context = context;
        this.profile = profile;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        profileImage = new ObservableField<>();
        name = new ObservableField<>((profile.getFirstName().equals(""))? null:profile.getFirstName());
        surname = new ObservableField<>((profile.getSurName().equals("")? null:profile.getSurName()));
        date = new ObservableField<>((profile.getBornDate().equals("")? null:getDate()));
        login = new ObservableField<>((profile.getLogin().equals("")? null:profile.getLogin()));
        email = new ObservableField<>((profile.getLogin().equals("")?null:profile.getEmail()));

//        date =

        Observable result = Observable.combineLatest(RxUtils.toObservable(name),
                RxUtils.toObservable(surname), RxUtils.toObservable(date),
                RxUtils.toObservable(login), RxUtils.toObservable(email),
                new Function5<String, String, String, String, String, Boolean>()
                {
                    @Override
                    public Boolean apply(String name, String surname, String date, String login, String email) throws Exception
                    {
                        int failCount = 0;
                        if (!InputValidator.validateUserName(name))
                        {
                            ++failCount;
                        }
                        if (!InputValidator.validateUserName(surname))
                        {
                            ++failCount;
                        }
                        if (!InputValidator.validateDate(date))
                        {
                            ++failCount;
                        }
                        if (!InputValidator.validateEmail(login))
                        {
                            ++failCount;
                        }
                        if (!InputValidator.validateUserName(email))
                        {
                            ++failCount;
                        }

                        return failCount == 0;
                    }
                }
//                new BiFunction<String, String, Boolean>() {
//                    @Override
//                    public Boolean apply(String login, String password) throws Exception {
//                        int failCount = 0;
//                        if (!InputValidator.validateUserName(login)) {
//                            ++failCount;
//                            loginErr.set("Username format not correct");
//                        } else {
//                            loginErr.set("");
//                        }
//
//                        if (!InputValidator.validatePassword(password)) {
//                            ++failCount;
//                            passwordErr.set("Password format not correct");
//                        } else {
//                            passwordErr.set("");
//                        }
//                        return failCount == 0;
//                    }
//                }
        );

        enableSave = RxUtils.toField(result);

//        clickOnSave = ()->
//        {
////            name = new ObservableField<>();
//            System.out.println(name.get());
//        };
//        clickOnPhoto = ()->
//        {
//            System.out.println("Hello");
//            startActivityForResult(chooserIntent, 999);
////            selectImage();
//            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
//            openGalleryIntent.setType("image/*");
//            startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
//        };

//            System.out.println("Hello");
//        };
        getInfoFromProfile();
    }

//    private void selectImage()
//    {
//        final CharSequence[] items = { "Take Photo", "Choose from Library",
//                "Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result= CheckUtility.checkPermission(context);
//
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask ="Take Photo";
//                    if(result)
//                        cameraIntent();
//
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask ="Choose from Library";
//                    if(result)
//                        galleryIntent();
//
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

//    public String getEmail()
//    {
//        if (!profile.getEmail().equals(""))
//            return profile.getEmail();
//        return null;
//    }

    private String getDate()
    {
        String pattern = "dd.mm.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        String formattedDate = formatter.format(LocalDate.now());
        if (profile.getBornDate() != null)
            return simpleDateFormat.format(profile.getBornDate());
        return null;
    }



    public Action getClickOnPhoto()
    {
        return clickOnPhoto;
    }

    public String getSurname()
    {
        if (!(profile.getSurName().equals("")))
            return profile.getSurName();
        return null;
    }

    public String getLogin()
    {
        if (!profile.getLogin().equals(""))
            return profile.getLogin();
        return null;
    }

//    public String getName()
//    {
//        name.set((profile.getFirstName().equals(""))? null:profile.getFirstName());
////        if (!(profile.getFirstName().equals("")))
////            return profile.getFirstName();
////        return null;
//    }

    public ObservableField<Drawable> getProfileImage()
    {
        return profileImage;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
        notifyChange();
    }

    public void getInfoFromProfile()
    {
//        setProgress(true);
        bindableFieldTarget = new BindableFieldTarget(profileImage, context.getResources());

        Picasso
                .get()
                .load((profile.getAvatarUrl() == null ||
                        profile.getAvatarUrl().equals("")?
                        Constant.EMPTY_PHOTO :
                        profile.getAvatarUrl()))
                .transform(new CropCircleTransformation())
//                .centerInside()
//            .fit()
                .into(bindableFieldTarget);

//        setProgress(false);
//        notifyChange();
//        notifyChange();
    }

}
