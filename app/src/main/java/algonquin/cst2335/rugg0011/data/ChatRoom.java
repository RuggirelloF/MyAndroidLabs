package algonquin.cst2335.rugg0011.data;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.rugg0011.R;
import algonquin.cst2335.rugg0011.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.rugg0011.databinding.ReceivedMessageBinding;
import algonquin.cst2335.rugg0011.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    private RecyclerView.Adapter<MyRowHolder> myAdapter;

    ChatRoomViewModel chatModel;

    ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView((binding.getRoot()));

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        messages = chatModel.messages.getValue();

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").build();
        mDAO = db.cmDAO();

        if(messages == null){
            chatModel.messages.postValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(mDAO.getAllMessages());
                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));
            });
        }

        binding.sendButton.setOnClickListener(click -> {
            String input = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd/MMM hh:mm a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(input, currentDateAndTime, true);
            messages.add(cm);

            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(cm);
            });
        });

        binding.receiveButton.setOnClickListener(click -> {
            String input = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd/MMM hh:mm a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(input, currentDateAndTime, false);
            messages.add(cm);

            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(cm);
            });
        });

        chatModel.selectedMessage.observe(this, (newValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newValue);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, chatFragment)
                    .commit();
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @Override
            public int getItemViewType(int position) {
                if(messages.get(position).getIsSentButton()){
                    return 0;
                }
                else{
                    return 1;
                }
            }

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding sentBinding = SentMessageBinding.inflate(getLayoutInflater());
                ReceivedMessageBinding receivedBinding = ReceivedMessageBinding.inflate(getLayoutInflater());
                if(viewType == 0){
                    return new MyRowHolder(sentBinding.getRoot());
                }
                else if(viewType == 1){
                    return new MyRowHolder(receivedBinding.getRoot());
                }
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                if(holder.getItemViewType() == 0) {
                    holder.sentMessageText.setText(obj.getMessage());
                    holder.sentTimeText.setText(obj.getTimeSent());
                }
                else if(holder.getItemViewType() == 1){
                    holder.receivedMessageText.setText(obj.getMessage());
                    holder.receivedTimeText.setText(obj.getTimeSent());
                }
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView sentMessageText;
        TextView sentTimeText;
        TextView receivedMessageText;
        TextView receivedTimeText;
        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(click -> {

                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);

            });

            sentMessageText = itemView.findViewById(R.id.sentMessage);
            sentTimeText = itemView.findViewById(R.id.sentTime);
            receivedMessageText = itemView.findViewById(R.id.receivedMessage);
            receivedTimeText = itemView.findViewById(R.id.receivedTime);
        }
    }
}
