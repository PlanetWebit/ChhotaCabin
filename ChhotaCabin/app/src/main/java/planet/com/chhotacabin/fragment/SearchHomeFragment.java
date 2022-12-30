package planet.com.chhotacabin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import planet.com.chhotacabin.R;


public class SearchHomeFragment extends Fragment  {
    String[] cabinType = {"Cabin Type", "Classic", "Premium", "Superme"};
    String[] person = {"Person", "1 to 6", "1 to 10", "1 to 20"};
    String[] Time = {"Time", "2 hours", "4 hours", "1 day"};
    Spinner cabintypeSpinner;
    Spinner personSpiner;
    Spinner spinTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchdashbord_home,container,false);
        cabintypeSpinner=view.findViewById(R.id.cabintypeSpinner);
        personSpiner=view.findViewById(R.id.personSpiner);
        spinTime=view.findViewById(R.id.spinTime);
        //cabintypeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,cabinType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        cabintypeSpinner.setAdapter(aa);

        ArrayAdapter personSpiner1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, person);
        personSpiner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        personSpiner.setAdapter(personSpiner1);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Time);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinTime.setAdapter(adapter);
        return view;

    }


}
