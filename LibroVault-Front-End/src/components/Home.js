import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; //import navigate used for changing "pages" or to redirect  
import CreateAccount from './CreateAccount'; // Import the CreateAccount component
import './Home.css'; //Import home.css for finished styling
import bookshelfImage from '../images/bookshelf.png'
import backgroundimage from '../images/bookshelf.png'
import homeImage from '../images/homeImage.png'
import addBookImage from '../images/add-book-image.png'
import libraryImg from '../images/library-sidebar.png'


/*"Home Componet is used as the main user interface(the login page) , has the features of log in and create user

*/ 
function Home() {

  //Initialize state variables for username and password and account visible
  //in a way like javas getter and setters but this one are designed to be manage the state of a component in a declaritve or reactive way. 
  //useState-> allows functional components to manage state , where state is used to store and manage data that can change over time AND 
  //affect how the componet renders 
  //state variables (username,password etc)- used to keep track of data that might change as as the user interacts with the component such as fields in the 
  //login page 
  //(' ') This basically makes it to when the components is renderd it would appear empty 
  //set"x" being any name , just changes/updates the state variables 

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isCreateAccountVisible, setCreateAccountVisible] = useState(false);
  const navigate = useNavigate();  //used to navigate to the main application 
  const [userId, setUserId] = useState('');
  const [currentImageIndex, setCurrentImageIndex] = useState(0); // Added this line


  //Just toggles the visibility of the create account form ( when clicked it shows)
  const toggleCreateAccount = () => {
    setCreateAccountVisible(!isCreateAccountVisible);
  };

  //Carl start here refrence the front the end code from erics branch *****************
  const handleLogin = () => {
    fetch(`http://localhost:8080/api/users/validate/${username}/${password}`, {
      method: 'GET',
    })
    .then((response) => {
      if (response.ok) {
        // Fetch the user's userId using the new endpoint
        return fetch(`http://localhost:8080/api/users/getUserId/${username}`, {
          method: 'GET',
        });
      } else {
        throw new Error('Invalid username or password');
      }
    })
    .then((userIdResponse) => userIdResponse.text()) // Assuming the response is just plain text (userId)
    .then((userId) => {
      if (userId) {
        setUserId(userId); // Set the userId
        navigate('/main-application', { state: { userId: userId } }); // Navigate with userId
      } else {
        alert('User ID not found');
      }
    })
    .catch((error) => {
      console.error('Login error:', error);
      alert(error.message);
    });
  };


    //if (username === user.username && password === user.password) {
    //  navigate('/main-application');
    //} else {
    //  alert('Invalid username or password');
    //}

    //placeholder ads for the bottom of the page
  const footerImages = [
    '/images/googlead1.png',
    '/images/googlead2.png',
    '/images/googlead3.png',
    '/images/googlead4.png',
    '/images/googlead5.png',
    '/images/googlead1.png',
    '/images/googlead2.png',
    '/images/googlead3.png',
    '/images/googlead4.png',
    '/images/googlead5.png'
  ];

  useEffect(() => {
    const timer = setInterval(() => {
      // Increment the index, and reset to 0 if it exceeds the array length
      setCurrentImageIndex((prevIndex) =>
        prevIndex === footerImages.length - 1 ? 0 : prevIndex + 1
      );
    }, 120000); // 120 seconds interval

    // Cleanup the timer on component unmount
    return () => clearInterval(timer);
  }, [footerImages.length]);

 //when styling this div use "home-container"
 //onChange{(e)} username or password  state variable in response to changes in the input field.
  return (
    <div className="home-container">
       {/* Displays the Title*/ }
      <h1>Welcome to Libro Vault</h1>
       {/*Input field for username*/ }
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
        {/*Input field for password*/ }
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
        {/*Button for loggin calls the handlelogin()*/ }
      <button onClick={handleLogin}>Log In</button>
        {/*Button for creating an account, toggles the visibility of the create account form*/ }
      <button onClick={toggleCreateAccount}>Create Account</button>
        {/*Conditionally render the create account component based on isCreateAccountVisible*/ }
      {isCreateAccountVisible && <CreateAccount />}
   
   {/*Eric - Sections added structure pretty straighforward*/}
   <div className='capsuleBox'>
       
       <section className='info-section'>
         <div className="informationSection">
           <h2>Organize your Literary World</h2>
           <p className="paragraph">
             Keep your entire book collection neatly organized! With our software, effortlessly categorize your books by title, author, genre, and more.
             Say goodbye to cluttered shelves and hello to an easily navigable digital library.
           </p>
           
         </div>
         <div className='section1Image'>
           <img className='firstImage' src={homeImage} alt="Bookshelf" />
         </div>
       </section>
      
     <section className='info-section2'>
       <div className='SecondinformationSection'>
         <h2>Personalized Libraries at your Fingertips</h2>
         <p className='paragraph'>
           Create custom libraries to suit your reading habits.Whether you're a fan of fiction, a devotee of documentaries,or a scholar of science, our software
           lets you curate collections that reflect your unique Literary taste
         </p>
       </div>
       <div className='secondImageTwo'>
         <img className='secondImage' src={addBookImage} alt="Bookshelf"/>
       </div>
     </section>
   
     <section className='info-section3'>
       <div className='thirdInformationSection'>
         <h2>Efficiently Manage Your Collection</h2>
         <p className='paragraph'>
             Discover the joy of efficient book managment! Our software offers advanced sorting features, allowing you to quickly locate and manage books. Spend less 
             time searching and more time reading with our intuiitve database system. 
         </p>
       </div>
       <div className='thirdImageThird'>
         <img className='thirdImage' src={libraryImg} alt="Bookshelf"/>
       </div>
     </section>
     

</div>
         {/* Footer section */}
         <div className="footer">
               {footerImages.map((image, index) => (
                 <img
                   key={index}
                   src={image}
                   alt={`Footer Image ${index + 1}`}
                   className={index === currentImageIndex ? 'visible' : 'hidden'}
                 />
               ))}
         </div>
    </div>
  );
  
  }
export default Home;
