package np.com.lashman.learnit.presentation.session

import np.com.lashman.learnit.domain.model.Session
import np.com.lashman.learnit.domain.model.Subject

data class SessionState(
    val subjects: List<Subject> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedToSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)
