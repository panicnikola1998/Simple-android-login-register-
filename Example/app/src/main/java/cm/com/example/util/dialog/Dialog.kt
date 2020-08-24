package cm.com.example.util.dialog

import android.content.Context
import cm.com.example.R

class Dialog {
    fun showLogoutDialog(context: Context,
                          onYesListener: ClickDialogListener.Yes
    ) {
        MessageDialog(context, "Đăng xuất", "Bạn có chắc chắn muốn đăng xuất?", true)
                .setColorTitle(R.color.colorPrimaryDark)
                .setButtonYesText("Đồng ý")
                .setButtonNoText("Hủy")
                .setClickYes { onYesListener.onCLickYes() }
                .show()
    }
}
