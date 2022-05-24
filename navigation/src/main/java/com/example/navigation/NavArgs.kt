package com.example.navigation


object OTPFragmentArgs {

  object Keys {
    const val SUBMIT_ACTION = "SUBMIT_ACTION"
    const val OTP_TOKEN = "OTP_TOKEN"
    const val PHONE = "PHONE"
  }

  object Values {
    const val ACTION_SIGNUP_CONFIRM = 1
    const val ACTION_LOGIN_CONFIRM = 2
    const val ACTION_FORGET_PASS = 3
  }
}

object NewPassFragmentArgs {

  object Keys {
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
  }
}

object CollectionDetailFragmentArgs {
  object Keys {
    const val TYPE = "TYPE"
    const val TITLE = "TITLE"
    const val QUERY_STRING = "QUERY_STRING"
  }
}

object UpdateProfileFragment {
  object Keys {
    const val PROFILE_INFO = "PROFILE_INFO"
  }
}

object ContentDetailsFragmentArgs {
  object Keys {
    const val CONTENT_ID = "CONTENT_ID"
    const val LINK_PLAY = "LINK_PLAY"
    const val START_POSITION = "START_POSITION"
    const val REQUEST_PIP = "REQUEST_PIP"
  }
}

object VodDetailPlayerFragmentArgs {
  object Keys {
    const val CONTENT_ID = "CONTENT_ID"
  }
}


object CreditDonateFragmentDialog {

  object Keys {
    const val USER_ID = "USER_ID"
    const val CONTENT_ID = "CONTENT_ID"
    const val NOTIFY_ON_SUCCESS = "NOTIFY_ON_SUCCESS"
  }
}

object LiveTvFragmentArgs {

  object Keys {
    const val CHANNEL_ID = "CHANNEL_ID"
  }
}

object OverlayPlayerFragmentArgs {

  object Keys {
    const val LINK_PLAY = "LINK_PLAY"
    const val START_POSITION = "START_POSITION"
  }
}

object DonateSuccessFragmentArgs{
  object Keys {
    const val TRANSACTIONID = "TRANSACTIONID"
  }
}

object TransactionHistoryDetailFragmentArgs {
  object Keys {
    const val TRANSACTION_DETAIL = "TRANSACTION_DETAIL"
  }
}

object ProfileFragmentArgs {

  object Keys {
    const val USER_ID = "USER_ID"
  }
}

object LiveFromDeviceFragment {
  object Keys {
    const val LIVE_ID = "LIVE_ID"
    const val RTMP_URL = "RTMP_URL"
  }
}

object WebViewFragmentArgs {
  object Keys {
    const val URL = "URL"
    const val TITLE = "TITLE"
    const val PREDEFINED = "PREDEFINED"
    const val RESULT_KEY = "WebViewFragmentResult"
  }

  object Values {
    const val PREDEFINED_TERM = 1
  }
}

object SubtitleFragmentArgs {
  object Keys {
    const val SUBTITLE_ARG = "SUBTITLE_ARG"
    const val SUBTITLE_RESPONSE_VALUE = "SUBTITLE_RESPONSE_VALUE"
    const val AUDIO_RESPONSE_VALUE = "AUDIO_RESPONSE_VALUE"
    const val PROFILE_RESPONSE_VALUE = "PROFILE_RESPONSE_VALUE"
  }
}

object EpisodeFragment{
  object Keys{
    const val EPISODE_THEME= "EPISODE_THEME"
  }
}

object CommentFragmentArgs {
  object Keys {
    const val COMMENT_ARG = "COMMENT_ARG"
    const val RESULT_ON_SUCCESS = "RESULT_ON_SUCCESS"
  }
}

object ChatFragmentArgs {
  object Keys {
    const val USER_ID = "USER_ID"
    const val ROOM_ID = "ROOM_ID"
    const val PROFILE_BINDING = "PROFILE_BINDING"
  }
}
