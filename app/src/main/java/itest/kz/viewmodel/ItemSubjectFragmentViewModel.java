package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.SubjectResponce;
import itest.kz.network.SubjectService;
import itest.kz.util.BindableFieldTarget;
import itest.kz.util.Constant;
import itest.kz.view.activity.MaterialsActivity;
import itest.kz.view.activity.TestActivity;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ItemSubjectFragmentViewModel extends BaseObservable
{
    private Subject subject;
    private Context context;
    private ObservableInt cardColor;
    public  Action clickMat;
    private ObservableField<Drawable> profileImage;
//    private ObservableInt colorIcon = new ObservableInt(Color.BLACK);
//    public ObservableInt buttonVisible;
    public ObservableInt textColor;
    public ObservableInt buttonsVisible;
    private BindableFieldTarget bindableFieldTarget;
    public boolean isClicked = false;
//    private List<Subject> subjectList;
//    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    public Action clickTest;


    public ItemSubjectFragmentViewModel(Subject subject, Context context)
    {
        this.subject = subject;
        this.context = context;
        this.cardColor = new ObservableInt(Color.parseColor("#D8DCE5"));
        profileImage = new ObservableField<>();
        getInfoFromProfile();
        if (subject.isExpand())
        {
            this.textColor = new ObservableInt(Color.parseColor("#36ABF9"));
        }
        else
        {
            this.textColor = new ObservableInt(Color.BLACK);
        }

//        buttonVisible = new ObservableInt(View.GONE);
        this.buttonsVisible = new ObservableInt(View.GONE);
//        int iconColor = Color.YELLOW; // TODO: Get from settings



//        clickTest =
    }

    public boolean checkSubLectureRecylle()
    {
        return subject.isExpand();

    }

    public String getSvg()
    {
        return subject.getIcon();
    }

    public int getColorIcon()
    {
        return Color.parseColor("#D8DCE5");
    }

    public int getCardColor ()
    {
        return Color.parseColor("#D8DCE5");
    }


    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
//        this.subjectList = new ArrayList<>();
        notifyChange();
    }

    public String getTitle()
    {
        return subject.getTitle();
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

//    public void onItemClick(View view)
//    {
//        if (!isClicked)
//        {
//            subject.setExpand(true);
//            buttonsVisible.set(View.VISIBLE);
//            textColor.set(Color.parseColor("#D8D8D8"));
//            isClicked = true;
////            colorIcon.set(Color.parseColor("#36ABF9"));
////            Drawable mDrawable = context.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp);
////            mDrawable.setColorFilter(new
////                    PorterDuffColorFilter(0x36ABF9,PorterDuff.Mode.MULTIPLY));
//        }
//        else
//        {
//            subject.setExpand(false);
//            buttonsVisible.set(View.GONE);
//            textColor.set(Color.BLACK);
////            colorIcon.set(Color.BLACK);
//            isClicked = false;
//        }
//
//
//        Log.d("dcsd","On Item Click");
//    }

//    public void clickTest(View view)
//    {
//
//        Intent intent = new Intent(getContext().getApplicationContext(), TestActivity.class);
//        intent.putExtra(Constant.SELECTED_SUBJECT,(Serializable) subject);
//        intent.putExtra(Constant.IS_STARTED_FIRST, true);
//        intent.putExtra(Constant.TYPE, Constant.TYPESUBJECTTEST);
////        context.startActivity(TestActivity.fillSelectedSubject(view.getContext(), subject));
//        context.startActivity(intent);
//    }

//    public void clickMat(View view)
//    {
//        if (subject != null) {
//            new Thread(new Runnable() {
//                @Override
//                public void run()
//                {
//                    try
//                    {
//                        Intent intent = new Intent(getContext().getApplicationContext(), MaterialsActivity.class);
//                        context.startActivity(MaterialsActivity.fillSelectedSubject(view.getContext(), subject));
//                    }
//                    catch (Exception e)
//                    {
//
//                    }
//
//                }
//            }).start();
//        }
//
//    }

    public ObservableField<Drawable> getProfileImage()
    {
        return profileImage;
    }

    public void getInfoFromProfile()
    {
//        setProgress(true);
        bindableFieldTarget = new BindableFieldTarget(profileImage, context.getResources());

        Picasso
                .get()
                .load(subject.getImage())
//                .load((profile.getAvatarUrl() == null ||
//                        profile.getAvatarUrl().equals("")?
//                        Constant.EMPTY_PHOTO :
//                        profile.getAvatarUrl()))
//                .transform(new CropCircleTransformation())
//                .centerInside()
//            .fit()
                .into(bindableFieldTarget);

//        setProgress(false);
//        notifyChange();
//        notifyChange();
    }

}
