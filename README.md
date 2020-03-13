<h1>SpiceRack Project</h1>
<h3>Project Details<h3>
<p>
As part of my "Mobiles" studies at Metropolia University of Applied Sciences I was to work within a team and develop a new Android application. Our team consisted of myself and Astrid Strohmaier. We learnt to run a project using SCRUM agile methodology with Microsoft Planner<a href="https://tasks.office.com/metropoliafi.onmicrosoft.com/Home/PlanViews/ijHnPAmgj0SZu35vFrZaj5YAHytL?Type=PlanLink&Channel=Link&CreatedTime=637196799774100000"> (Link).</a> The main documentation can also be found in SpiceRack - project files along with the APK. 
</p>
<h3>Self-Evaluation of Project<h3>    
<p>
SpiceRack was my first attempt at devloping an Android application. It started out as a simple problem that I and many other people could relate with, "Why do I always end up with multiple of the same herbs and spices in my cupboards at home?". The answer was straight forward, "you cannot always remember what you have in your cupboards when doing your shopping!". The solution was simple too, create a way to list your personal stash of herbs and spices. Here in SpiceRack was born.     
</p>
<p>
I was instantly hooked on the idea of a herb and spice inventory. I spent many hours over the 3 month project period learning, experiementing, testing, breaking and fixing. The cycle was endless even doing the simplest of tasks took time. I loved every second of it. Slowly as my knowledge grew, so did our application, eventually after many long coding sessions into the early hours of the morning we were done. The wave of excitement hit us. We did it. All the challenges we faced in creating custom recyclerView adapters or creating databases and building the best UI experience we could. It was all worth it in the end.      
</p>
<p>
Safe to say the application was well recieved by our peers in class and the lecturer. We presented our project and gave demonstrations to everyone. Feedback was very positive and so we won best in class from the peer review. Our lecturer prasied our management of the project citing how we used GitHub extensively, our level of commenting of the code and our focus on bug testing as key attributes. 
</p>
<p>
But as they say nothing is perfect. Areas that we could have improved on were usability design factors. It became quite apparent after multiple people used the app that they struggled with the initial understanding of how to get started. As the developer I became immune to this as I already knew how the application worked therefore didnt see this initial barrier. This was not a deal breaker however, once people became familiar it became a lot easier to use. The learning experience from this made me realise that I need to get more people involved in the test of the UI earlier in development to ensure maximum useablity. It also highlighted that I still have a lot to learn about UI design, a focus that I will work on in my future projects.
</p>
<h3>About SpiceRack</h3>
<p>
    SpiceRack provides you with an intuitive user experience that simplifies your spice & herb
    shopping. SpiceRack is easy to use and visually appealing. With our built-in barcode
    scanner, you can add your favourite spices & herbs easily to your custom inventory. The
    custom inventory is a blank slate, you build as you go only tracking the herbs and spices you use.
</p>
<p>
    SpiceRack can also be used as part of your shopping management routine - it enables you
    to quickly add spices & herbs to a shopping list by automatically add inventory items
    to your list when they reach zero. Additional none inventory items can added to the shopping list
    through manually typing.
</p>
<p>
    Authors: Michael Carter and Astrid Strohmaier
</p>
<p>
    <h3>Demonstration Video</h3>
        <a href="https://drive.google.com/open?id=1uyVilUMhCDWAF9LXydn2RUfhkYYZRi57">Demonstration Video</a>
        <br>
    <h3>JavaDoc's</h3>
    <a href="https://users.metropolia.fi/~astridst/javadoc/index.html">JavaDoc's</a>
</p>
    
<h1>Instructions</h1>
<h3>Installation</h3>
<p>
    <ol>
        <li>On your phone, go to Settings > System > About phone and tap “Build number” 7 times.</li>
        <li>In the Settings menu go to “Developer options” at the bottom. In the “Developer options” menu, scroll down and enable “USB debugging”.</li>
        <li>Connect the USB cable to your phone and install the app via the *.apk file you received. Once the installation has finished, start the app.</li>
    </ol>
</p>
<h3>Sign Up</h3>
<p>
    <ol>
        <li>Click on the “Sign up” button</li>
        <li>Fill out the Sign-up form make sure all fields are completed.</li>
    </ol>
</p>
<h3>Adding Spices to the Inventory</h3>
<p>
    <ol>
        <li>Navigate to “Spice Inventory Editor” screen by swiping left to right twice.</li>
        <li>Tap on the icon in the lower right corner. Once prompted, please allow SpiceRack to access your camera. This is necessary to scan the barcode of spices.</li>
        <li>Scan the barcode of the spice you want to add.</li>
        <li>Fill all the remaining fields with the relevent information. Then press "Add Spice".</li>
    </ol>
</p>
<h3>Inventory Maintenance</h3>
<p>
    <h5>Adjusting stock</h5>
    <ol>
        <li>Navigate to "Spice Inventory" screen.</li>
        <li>All spices currently in your inventory will be visible. Click on any item to get more details.</li>
        <li>On the "Spice Information" screen will display all the information related to spice that was pressed. It also lists all spices with the same name.</li>
        <li>Swiping left to right will subtract 1 from the stock until it is at 0. Swiping right to left will add 1 to the stock until the max 99 is hit.</li>
    </ol>
    <p>or</p>
    <ol>
        <li>On the "Home" screen swipe top to bottom or press "Scan".</li>
        <li>Scan barcode of spice that were purchased.</li>
        <li>On the "Spice Information" screen will display all the information related to spice that was pressed. It also lists all spices with the same name.</li>
        <li>Swiping left to right will subtract 1 from the stock until it is at 0. Swiping right to left will add 1 to the stock until the max 99 is hit.</li>
    </ol>
</p>
<p>
    <h5>Deleting Spices</h5>
    <ol>
        <li> On the "Spice Inventory Editor" scan or type the barcode that needs to be deleted.</li>
        <li>Press "Delete Spice".</li>
    </ol>
</p>
<p>
    <h5>Updating Spices</h5>
    <ol>
        <li> On the "Spice Inventory Editor" scan or type the barcode that needs to be updated.</li>
        <li>fill the rest of the empty fields with the new "orrect" infomation.</li>
        <li>Press "Update Spice".</li>
    </ol>
</p>
<p>
    <h5>Checking Inventory</h5>
    <ol>
        <li>On the "Home" screen swipe top to bottom or press "Scan".</li>
        <li>Scan barcode of spice that is being queried.</li>
        <li>"Spice Information" screen will display any stock you currently have or if the spice does not exist.</li>
    </ol>
</p>
<h3>Shopping List</h3>
<p>
    <ol>
        <li>Navigate to the “Shopping List”</li>
        <li>Any spices from the inventory with stock 0 will be automatically added to the shopping list. (The shopping list is unique by item name so cannot hold more than one item with the same name).</li>
        <li>Additional none spice items can be manually added to shopping list by completing the fields and pressing "Add Item".</li>
        <li>Each item on the list is displayed and when pressed a strike through appears. "Crossing it off the list".</li>
        <li>All items with a strike through when the "Clear List" is pressed are removed.</li>
        <li>Swiping a item left to right will remove it from the list.</li>
        <li>Swiping a item right to left will update the item with new details from the entry fields.</li>
    </ol>
</p>
<h3>Profile</h3>
<p>
    <ol>
        <li>Navigate to the "Profile" screen.</li>
        <li>Current logged in User details are shown here.</li>
        <li>Update user details by fillig in fields with correct infomation and pressing "Edit Profile".</li>
        <li>User can also be deleted by pressing "Delete Account".</li>
        <li>"About" can be pressed to show details about SpiceRack.</li>
        <li>On "Home" screen, press log out when wanting to sign out of the application. Automatic Login is in place if you close the application without logging out first.</li>
    </ol>
</p>
<h3>Reference and Credit</h3>
<p>
    <h5>ClipArt</h5>
    <ol>
        <li>Speech Bubble <a href="https://pixabay.com/users/OpenClipart-Vectors-30363/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=160786">OpenClipart-Vectors</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=160786">Pixabay</a></li>
        <li>Info <a href="https://pixabay.com/users/Nikin-253338/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=2150938">Nikin</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=2150938">Pixabay</a></li>
        <li>Flowers <a href="https://pixabay.com/users/Suxu-269261/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=817486">Suxu</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=817486">Pixabay</a></li>
        <li>Barcode <a href="https://pixabay.com/users/Clker-Free-Vector-Images-3736/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=306926">Clker-Free-Vector-Images</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=306926">Pixabay</a></li>
        <li>Garlic <a href="https://pixabay.com/users/Clker-Free-Vector-Images-3736/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=25382">Clker-Free-Vector-Images</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=25382">Pixabay</a></li>
        <li>Red Chili <a href="https://pixabay.com/users/Clker-Free-Vector-Images-3736/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=296655">Clker-Free-Vector-Images</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=296655">Pixabay</a></li>
        <li>Black Pepper <a href="https://pixabay.com/users/OpenClipart-Vectors-30363/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=575441">OpenClipart-Vectors</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=575441">Pixabay</a></li>
        <li>Ginger <a href="https://pixabay.com/users/OpenClipart-Vectors-30363/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=1293466">OpenClipart-Vectors</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=1293466">Pixabay</a></li>
        <li>Pestle&Mortar <a href="https://pixabay.com/users/OpenClipart-Vectors-30363/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=576444">OpenClipart-Vectors</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=576444">Pixabay</a></li>
        <li>Chili Line <a href="https://pixabay.com/users/OpenClipart-Vectors-30363/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=575843">OpenClipart-Vectors</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=575843">Pixabay</a></li>
    </ol>
    <h5>Barcode Scanner</h5>
    <ol>
        <li>Barcode scanner code Library - ZXing</li>
        <li>https://github.com/zxing/zxing</li>
    </ol>
</p>
