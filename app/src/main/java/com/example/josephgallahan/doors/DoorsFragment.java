package com.example.josephgallahan.doors;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by joseph.gallahan on 9/30/2016.
 */

public class DoorsFragment extends Fragment
{
    private Doors mDoor;
    private int mPrizeDoor, mChosenDoor, mXDoor;  //All the doors
    private ImageButton[] mDoors;                 //All the imagebuttons for the doors
    private Random mRandom = new Random();
    private Button mYesButton, mNoButton, mResetButton;
    TextView mOutput;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDoor = new Doors();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_doors, container, false);

        mPrizeDoor = mRandom.nextInt(3);    //Get a random num between 0-2 inclusive

        //Set Door Buttons
        mDoors = new ImageButton[] {
                (ImageButton) v.findViewById(R.id.door_button_0),
                (ImageButton) v.findViewById(R.id.door_button_1),
                (ImageButton) v.findViewById(R.id.door_button_2)};
        mOutput = (TextView) v.findViewById(R.id.output);


        //Put click listeners on the door buttons
        for (int i = 0; i < mDoors.length; i++)
        {
            mDoors[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Get chosen door num
                    for (int i = 0; i < 3; i++)
                    {
                        if (mDoors[i].equals(v))
                        {
                            mChosenDoor = i;
                            //Show the number of the chosen door
                            mOutput.setText("You chose door number " + i);
                        }
                    }
                    revealBadDoor();            //Reveal a losing door
                    toggleChoicePrompts(true);  //Display the next step
                }
            });
        }

        //Set other buttons
        mResetButton = (Button) v.findViewById(R.id.reset_button);
        mYesButton = (Button) v.findViewById(R.id.yes_button);
        mNoButton = (Button) v.findViewById(R.id.no_button);

        //Put click listeners on the other buttons
        mResetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = getActivity().getIntent();
                getActivity().finish();
                startActivity(i);
            }
        });
        mYesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchDoor();
                endGame();
            }
        });
        mNoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                endGame();
            }
        });

        return v;
    }

    //When the user taps on a door, reveal a non-prize door
    private void revealBadDoor()
    {
        Random r = new Random();
        ImageButton door;
        int revealDoor = mPrizeDoor;

        //Get a random door that doesn't contain the prize
        while (revealDoor == mPrizeDoor || revealDoor == mChosenDoor)
        {
            revealDoor = r.nextInt(3);
        }

        mXDoor = revealDoor;

        //Get the image button for the door
        switch(revealDoor)
        {
            case 0:door = mDoors[0];
                break;
            case 1:door = mDoors[1];
                break;
            case 2:door = mDoors[2];
                break;
            default:door = mDoors[0];
        }

        //Put an X on top of the door and disable all doors
        door.setImageResource(R.drawable.x_mark);
        disableTapping();
    }

    //Prompts the user to choose to either choose another door or keep their current one
    private void toggleChoicePrompts(boolean toggle)
    {
        //Get all the layouts
        LinearLayout[] prompts;
        prompts = new LinearLayout[]
                {
                        (LinearLayout) getActivity().findViewById(R.id.prompt1),
                        (LinearLayout) getActivity().findViewById(R.id.prompt2),
                        (LinearLayout) getActivity().findViewById(R.id.prompt3)
                };

        //Make all the layouts visible
        for (int i = 0; i < 3; i++)
        {
            if (toggle)
                prompts[i].setVisibility(View.VISIBLE);

            else
                prompts[i].setVisibility(View.GONE);
        }
    }

    //Allows the user to switch doors
    private void switchDoor()
    {
        toggleChoicePrompts(false);

        if (mXDoor == 0)
        {
            if (mChosenDoor == 1)
            {
                mChosenDoor = 2;
            }
            else
            {
                mChosenDoor = 1;
            }
        }
        else if (mXDoor == 1)
        {
            if (mChosenDoor == 0)
            {
                mChosenDoor = 2;
            }
            else
            {
                mChosenDoor = 0;
            }
        }
        else
        {
            if (mChosenDoor == 0)
            {
                mChosenDoor = 1;
            }
            else
            {
                mChosenDoor = 0;
            }
        }

        mOutput.setText("Door swapped to door number " + mChosenDoor);
    }

    //What happens when the game is over
    private void endGame()
    {
        toggleChoicePrompts(false);

        mResetButton.setVisibility(View.VISIBLE);

        //Show whether you win or lose
        if (mChosenDoor == mPrizeDoor)
            mOutput.setText(mOutput.getText() + "\nYou win!");
        else
            mOutput.setText(mOutput.getText() + "\nYou Lose! " + mPrizeDoor + " was the correct door");

        //Display Picture
        if (mChosenDoor == mPrizeDoor)
            mDoors[mChosenDoor].setImageResource(R.drawable.medallion);
        else
            mDoors[mChosenDoor].setImageResource(R.drawable.donkey);

        disableTapping();
    }

    //Disable tapping on doors
    private void disableTapping()
    {
        for (int i = 0; i < 3; i++)
        {
            mDoors[i].setClickable(false);
        }
    }
}
