package cm.com.example.util.dialog

class ClickDialogListener {
    interface Yes {
        fun onCLickYes()
    }

    interface OK {
        fun onCLickOK()
    }

    interface No {
        fun onClickNo()
    }
}