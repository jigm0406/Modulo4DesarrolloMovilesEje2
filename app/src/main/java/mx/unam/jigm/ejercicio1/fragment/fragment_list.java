package mx.unam.jigm.ejercicio1.fragment;



/**
 * Created by Mario on 13/06/2016.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import mx.unam.jigm.ejercicio1.ActivityDetail;
import mx.unam.jigm.ejercicio1.R;
import mx.unam.jigm.ejercicio1.adapters.AdapterItemList;
import mx.unam.jigm.ejercicio1.detalle.DetalleLista;
import mx.unam.jigm.ejercicio1.model.ModelItem;

import java.util.ArrayList;
import java.util.List;

public class fragment_list extends Fragment{
    private ListView listView;
    private List<ModelItem> array = new ArrayList<>();
    private int counter;
    private boolean isWifi;

    @Nullable
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        listView = (ListView) view.findViewById(R.id.list_items);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterItemList adapter= (AdapterItemList) parent.getAdapter();
                ModelItem modelItem =adapter.getItem(position);
                ModelItem modelItem2 = array.get(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(),DetalleLista.class);
                //se obtienenlos datos a enviar
                intent.putExtra("dato",modelItem2.item);
                intent.putExtra("imagen",modelItem2.resourceId);
                //llama a la activity de perfil de lista
                startActivity(intent);
            }
        });
        final EditText mItemsText = (EditText) view.findViewById(R.id.mItemText);
        view.findViewById(R.id.btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemData = mItemsText.getText().toString();
                if(!TextUtils.isEmpty(itemData))
                {
                    ModelItem item =new ModelItem();
                    item.item=itemData;
                    item.id  = "Description "+counter;
                    item.resourceId=isWifi?R.drawable.ic_action_account_circle:R.drawable.ic_action_account_child;
                    array.add(item);
                    listView.setAdapter(new AdapterItemList(getActivity(),array));
                    isWifi=!isWifi;
                    counter++;
                    mItemsText.setText("");
                }
            }
        });
        return view;
    }


}
