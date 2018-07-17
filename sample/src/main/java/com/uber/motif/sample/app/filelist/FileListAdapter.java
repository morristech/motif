package com.uber.motif.sample.app.filelist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.uber.motif.sample.app.filerow.FileRowController;
import com.uber.motif.sample.app.filerow.FileRowView;
import com.uber.motif.sample.app.filerow.FileTouches;
import io.reactivex.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> implements FileTouches {

    private final FileListScope scope;
    private final Relay<File> fileTouches = PublishRelay.create();

    private List<File> files = new ArrayList<>();

    public FileListAdapter(FileListScope scope) {
        this.scope = scope;
    }

    public void setFiles(List<File> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @Override
    public Observable<File> touches() {
        return fileTouches;
    }

    @NonNull
    @Override
    public FileListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FileRowView view = FileRowView.create(parent.getContext(), parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileListAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final FileRowView view;

        @Nullable private File file;
        @Nullable private FileRowController controller;

        ViewHolder(FileRowView view) {
            super(view);
            this.view = view;

            view.setOnTouchListener((v, event) -> {
                if (file != null) {
                    fileTouches.accept(file);
                }
                return false;
            });
        }

        void bind(int position) {
            unbind();
            file = files.get(position);
            controller = scope.fileRow(view, file).controller();
            controller.attach();
        }

        void unbind() {
            if (controller != null) {
                controller.detach();
            }

            file = null;
            controller = null;
        }
    }
}