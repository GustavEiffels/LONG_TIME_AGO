// Generated by view binder compiler. Do not edit!
package com.sing.board4_3.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sing.board4_3.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentBoardWriteBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView boardWriteImage;

  @NonNull
  public final EditText boardWriteSubject;

  @NonNull
  public final EditText boardWriteText;

  @NonNull
  public final Toolbar boardWriteToolbar;

  @NonNull
  public final Spinner boardWriteType;

  @NonNull
  public final TextView textView;

  private FragmentBoardWriteBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView boardWriteImage, @NonNull EditText boardWriteSubject,
      @NonNull EditText boardWriteText, @NonNull Toolbar boardWriteToolbar,
      @NonNull Spinner boardWriteType, @NonNull TextView textView) {
    this.rootView = rootView;
    this.boardWriteImage = boardWriteImage;
    this.boardWriteSubject = boardWriteSubject;
    this.boardWriteText = boardWriteText;
    this.boardWriteToolbar = boardWriteToolbar;
    this.boardWriteType = boardWriteType;
    this.textView = textView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentBoardWriteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentBoardWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_board_write, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentBoardWriteBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.board_write_image;
      ImageView boardWriteImage = ViewBindings.findChildViewById(rootView, id);
      if (boardWriteImage == null) {
        break missingId;
      }

      id = R.id.board_write_subject;
      EditText boardWriteSubject = ViewBindings.findChildViewById(rootView, id);
      if (boardWriteSubject == null) {
        break missingId;
      }

      id = R.id.board_write_text;
      EditText boardWriteText = ViewBindings.findChildViewById(rootView, id);
      if (boardWriteText == null) {
        break missingId;
      }

      id = R.id.board_write_toolbar;
      Toolbar boardWriteToolbar = ViewBindings.findChildViewById(rootView, id);
      if (boardWriteToolbar == null) {
        break missingId;
      }

      id = R.id.board_write_type;
      Spinner boardWriteType = ViewBindings.findChildViewById(rootView, id);
      if (boardWriteType == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new FragmentBoardWriteBinding((LinearLayout) rootView, boardWriteImage,
          boardWriteSubject, boardWriteText, boardWriteToolbar, boardWriteType, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
