package model

import java.util.UUID
import models._

case class Ad(
     id: UUID,
     resume: Resume,
     detail: Detail,
     source: Source
)
