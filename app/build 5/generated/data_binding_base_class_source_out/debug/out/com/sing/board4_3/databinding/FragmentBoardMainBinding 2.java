// Generated by view binder compiler. Do not edit!
package com.sing.board4_3.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sing.board4_3.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentBoardMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RecyclerView boardMainRecycler;

  @NonNull
  public final SwipeRefreshLayout boardMainSwipe;

  @NonNull
  public final Toolbar boardToolbar;

  @NonNull
  public final SearchView searchView;

  private FragmentBoardMainBinding(@NonNull LinearLayout rootView,
      @NonNull RecyclerView boardMainRecycler, @NonNull SwipeRefreshLayout boardMainSwipe,
      @NonNull Toolbar boardToolbar, @NonNull SearchView searchView) {
    this.rootView = rootView;
    this.boardMainRecycler = boardMainRecycler;
    this.boardMainSwipe = boardMainSwipe;
    this.boardToolbar = boardToolbar;
    this.searchView = searchView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentBoardMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentBoardMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_board_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentBoardMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.board_main_recycler;
      RecyclerView boardMainRecycler = ViewBindings.findChildViewById(rootView, id);
      if (boardMainRecycler == null) {
        break missingId;
      }

      id = R.id.board_main_swipe;
      SwipeRefreshLayout boardMainSwipe = ViewBindings.findChildViewById(rootView, id);
      if (boardMainSwipe == null) {
        break missingId;
      }

      id = R.id.board_toolbar;
      Toolbar boardToolbar = ViewBindings.findChildViewById(rootView, id);
      if (boardToolbar == null) {
        break missingId;
      }

      id = R.id.search_view;
      SearchView searchView = ViewBindings.findChildViewById(rootView, id);
      if (searchView == null) {
        break missingId;
      }

      return new FragmentBoardMainBinding((LinearLayout) rootView, boardMainRecycler,
          boardMainSwipe, boardToolbar, searchView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
