package febri.uray.bedboy.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Responses(
    @SerializedName("total_count")
    var totalCount: Int? = null,

    @SerializedName("incomplete_results")
    var inCompleteResults: Boolean? = null,

    @SerializedName("items")
    var items: List<DataResponse>
) : Parcelable
