import axios from 'axios'

const USER = 'codera'
const BUG_API_URL = 'http://localhost:8080'
const USER_API_URL = `${BUG_API_URL}/${USER}`

/**
* Turning frontend user inputs into API functions for Bug objects. 
*/
class BugDataService {

	/** Constructor */
	BugDataService(){
	}

    retrieveList() {
        return axios.get(`${USER_API_URL}/bugs/`);
    }
    
    createItem(item) {
    	//console.log('add task detected')
        return axios.post(`${USER_API_URL}/bugs/`, item);
    }
    
    updateItem(id, item) {
        return axios.put(`${USER_API_URL}/bugs/${id}`, item);
  	}
    
    deleteItem(id) {
	    console.log('delete task detected')
	    return axios.delete(`${USER_API_URL}/bugs/${id}`);
	}
	
	retrieveItem(id) {
    	return axios.get(`${USER_API_URL}/bugs/${id}`);
    } 
    
    onSubmit(values) {
	    console.log(values);
	}
	
} export default new BugDataService()
