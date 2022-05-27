package com.example.dclassmanagement.data

const val ACTION_CLASS_PICK = "ACTION_CLASS_PICK"

object Action {
    object Item {
        const val PICK_CLASS = "PICK_CLASS"
        const val LIKE_POST = "LIKE_POST"
        const val COMMENT_POST = "COMMENT_POST"
        const val ITEM_CHECK = "ITEM_CHECK"
        const val ITEM_UNCHECK = "ITEM_UNCHECK"
    }
}

object FragmentChatARGS{
    object KEY{
        const val USER = "USER"
        const val USER_ID = "USER_ID"
    }
}

object FragmentLandingPageARGS{
    object KEY{
        const val CLASS_ID = "CLASS_ID"
        const val OWNER_ID = "OWNER_ID"
    }
}

object FragmentHomeARGS{
    object KEY{
        const val POST_ID = "POST_ID"
        const val POST = "POST"
    }
}

object FragmentAssignmentARGS{
    object KEY{
        const val ASSIGNMENT = "ASSIGNMENT"
    }
}

object Table{
    const val USER_CLASS = "UserClass"
    const val USER = "User"
    const val CLASS = "Class"
    const val POST = "Post"
    const val COMMENT = "Comment"
    const val LIKE = "Like"
    const val ASSIGNMENT = "Assignment"
    const val ASSIGNMENT_USER = "AssignmentUser"
    const val CHAT = "Chat"
    const val MESSAGE = "Message"
}