import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

  //Initialize state variables for username and password and account visible
  //in a way like javas getter and setters but this one are designed to be manage the state of a component in a declaritve or reactive way. 
  //useState-> allows functional components to manage state , where state is used to store and manage data that can change over time AND 
  //affect how the componet renders 
  //state variables (username,password etc)- used to keep track of data that might change as as the user interacts with the component such as fields in the 
  //login page 
  //(' ') This basically makes it to when the components is renderd it would appear empty 
  //set"x" being any name , just changes/updates the state variables 
function CreateAccount() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rePassword, setRePassword] = useState('');
  const navigate = useNavigate(); // Define the navigate function
  const [userId, setUserId] = useState('');


// user makes new account
  const handleCreateAccount = (user) => {

    const userData = {
      username,
      password,
    };


    if (password !== rePassword) {
      alert('The password fields do not match. Please try again.');
    } 
    else if (username == '' || password == '') {
      alert('The password or username fields are empty. Please try again')
    }
    else if (/\s/.test(username) || /\s/.test(password)) {
      alert('Empty spaces are not allowed in your username or password. Please try again.')
    }
    else {
      fetch('http://localhost:8080/api/users/createUser', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      })
        .then((response) => response.json())
        .then((user) => {
          if (user.id) {
            //update this for better response to useer
            console.log('New user created with ID:', user.id);
            setUserId(user.id);
          } else {
            console.error('User creation failed.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          // handle network errors or other issues
      })

      // Simulate automatic login after a delay (e.g., 3 seconds)
      // setTimeout(() => {
      //   // Replace with your actual login logic
      //   // For now, automatically fill in the login fields and trigger login
      //   setUsername('u');
      //   setPassword('p');
      //   handleLogin();
      // }, 3000);
    }
  };

  const handleDeleteAccount = (userToDelete) => {
    if (userId) {
      // make DELETE request to backend API to delete user
      fetch(`http://localhost:8080/api/users/remove/${userId}` , {
        method: 'DELETE',
      })
        .then((response) => {
          if (response.ok) {
            console.log('User deleted successfully');
          } else {
            console.error('Failed to delete user');
            // handle errors and provide user feedback for failure
          }
        })
        .catch((error) => {
          console.error('Error:', error);
            // handle network errors or other issues
        });
    } else {
      console.error('userID is missing or invalid');
      // case where userID doesn't exist
    }
  }

  return (
    <div className="create-account-container">
      <h2>Create Account</h2>
      <form>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Enter Username"
        />

        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Enter Password"
        />

        <input
          type="password"
          value={rePassword}
          onChange={(e) => setRePassword(e.target.value)}
          placeholder="Re-Type Password"
        />

        <button type="button" onClick={handleCreateAccount}>
          Create Account
        </button>
      </form>
    </div>
  );
}

export default CreateAccount;
