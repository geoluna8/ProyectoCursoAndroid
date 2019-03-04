package com.code3e.luna080119.appgeovanny.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.code3e.luna080119.appgeovanny.Fragments.AboutFragment
import com.code3e.luna080119.appgeovanny.Fragments.CarsFragment
import com.code3e.luna080119.appgeovanny.Fragments.ContactsFragment
import com.code3e.luna080119.appgeovanny.Fragments.MapsFragment

//Nos ayuda a configurar el comportamiento de un ViewPager

class TabsAdapter : FragmentPagerAdapter {

    //constructor para iniciar el adaptador
    constructor(fragmentManager: FragmentManager) : super(fragmentManager)

    //Este método define cuantas paginas tendrá el ViewPager
    override fun getCount(): Int {
        return 4
    }

    //Este metodo define que Fragmento se va muestra por pagina
    //p0 es el numero de pagina
    override fun getItem(p0: Int): Fragment {
        when(p0){
            0 -> return CarsFragment()
            1 -> return MapsFragment()
            2 -> return ContactsFragment()
            3 -> return AboutFragment()
        }
        return Fragment()
    }

}