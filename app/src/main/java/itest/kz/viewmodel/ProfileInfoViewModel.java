package itest.kz.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import io.reactivex.functions.Action;
import itest.kz.model.Profile;
import itest.kz.util.BindableFieldTarget;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileInfoViewModel extends BaseObservable
{
    private Context context;
    private BindableFieldTarget bindableFieldTarget;
    private Profile profile;
    private ObservableField<Drawable> profileImage;
    private Action clickOnPhoto;
    private static final int REQUEST_GALLERY_CODE = 200;

    public ProfileInfoViewModel( Context context, Profile profile)
    {
        this.context = context;
        this.profile = profile;
        profileImage = new ObservableField<>();
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

    public String getDate()
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

    public String getName()
    {
        if (!(profile.getFirstName().equals("")))
            return profile.getFirstName();
        return null;
    }

    public ObservableField<Drawable> getProfileImage()
    {
        return profileImage;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void getInfoFromProfile()
    {
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
//        notifyChange();
//        notifyChange();
    }

}
