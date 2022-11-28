package algonquin.cst2335.rugg0011.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import algonquin.cst2335.rugg0011.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage m){
        selected = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.detailsMessage.setText("Message: " + selected.message);
        binding.detailsTime.setText("Sent: " + selected.timeSent);

        if (selected.isSentButton == true){
            binding.detailsSendReceive.setText("Message sent by: You");
        }
        else{
            binding.detailsSendReceive.setText("Message sent by: Other");
        }

        binding.detailsDatabaseID.setText("ID = "+ selected.id);

        return binding.getRoot();

    }



}
