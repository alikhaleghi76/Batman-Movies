package ali.khaleghi.batman.view.ui.custom;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtils {

    public static void setHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderFooterAdapter)) {
            return;
        }

        HeaderFooterAdapter headerAndFooterAdapter = (HeaderFooterAdapter) outerAdapter;
        if (headerAndFooterAdapter.getHeaderViewsCount() == 0) {
            headerAndFooterAdapter.addHeaderView(view);
        }
    }

    public static void setFooterView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderFooterAdapter)) {
            return;
        }

        HeaderFooterAdapter headerAndFooterAdapter = (HeaderFooterAdapter) outerAdapter;
        if (headerAndFooterAdapter.getFooterViewsCount() == 0) {
            headerAndFooterAdapter.addFooterView(view);
        }
    }

    public static void removeFooterView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof HeaderFooterAdapter) {

            int footerViewCounter = ((HeaderFooterAdapter) outerAdapter).getFooterViewsCount();
            if (footerViewCounter > 0) {
                View footerView = ((HeaderFooterAdapter) outerAdapter).getFooterView();
                ((HeaderFooterAdapter) outerAdapter).removeFooterView(footerView);
            }
        }
    }

    public static void removeHeaderView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof HeaderFooterAdapter) {

            int headerViewCounter = ((HeaderFooterAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                View headerView = ((HeaderFooterAdapter) outerAdapter).getHeaderView();
                ((HeaderFooterAdapter) outerAdapter).removeFooterView(headerView);
            }
        }
    }

    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderFooterAdapter) {

            int headerViewCounter = ((HeaderFooterAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }

        return holder.getLayoutPosition();
    }

    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderFooterAdapter) {

            int headerViewCounter = ((HeaderFooterAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - headerViewCounter;
            }
        }

        return holder.getAdapterPosition();
    }
}
