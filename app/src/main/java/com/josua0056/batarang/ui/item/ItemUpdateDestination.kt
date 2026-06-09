package com.josua0056.batarang.ui.item

import com.josua0056.batarang.R
import com.josua0056.batarang.ui.navigation.NavigationDestination

object ItemUpdateDestination : NavigationDestination {
    override val route = "item_update"
    override val titleRes = R.string.edit_geometri
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}
