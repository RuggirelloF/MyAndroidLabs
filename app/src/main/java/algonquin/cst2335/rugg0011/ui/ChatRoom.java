package algonquin.cst2335.rugg0011.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.rugg0011.R;
import algonquin.cst2335.rugg0011.data.ChatMessage;
import algonquin.cst2335.rugg0011.data.ChatMessageDAO;
import algonquin.cst2335.rugg0011.data.ChatRoomViewModel;
import algonquin.cst2335.rugg0011.data.MessageDatabase;
import algonquin.cst2335.rugg0011.databinding.ActivityChatRoomBinding;
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

            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd/MMM/yyyy hh:mm");
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

            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd/MMM/yyyy hh:mm");
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

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(click -> {
                int position = getAbsoluteAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                        .setTitle("Question: ")
                        .setNegativeButton("No", (dialog, cl) -> {})
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            ChatMessage m = messages.get(position);
                            mDAO.deleteMessage(m);

                            Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", click1 -> {

                                        Executor thread = Executors.newSingleThreadExecutor();
                                        thread.execute(() -> {
                                            mDAO.insertMessage(m);
                                        });

                                        messages.add(position, m);
                                        myAdapter.notifyItemInserted(position);
                                    }).show();

                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() -> {
                                mDAO.deleteMessage(m);
                            });

                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);

                }).create().show();

            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}

