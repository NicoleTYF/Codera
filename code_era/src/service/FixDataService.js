import axios from 'axios'

const USER = 'code_era'
const FIX_API_URL = 'http://localhost:8080'
const USER_API_URL = `${FIX_API_URL}/${USER}`

/**
* Turning frontend user inputs into API functions for Fix objects. 
*/
class FixDataService {

	/** Constructor */
	FixDataService(){
	}

    retrieveList(bugId) {
        return axios.get(`${USER_API_URL}/bugs/${bugId}/fixes/`);
    }
    
    createItem(bugId, item) {
    	//console.log('add fix object detected')
        return axios.post(`${USER_API_URL}/bugs/${bugId}/fixes/`, item);
    }

	updateItem(bugId, id, item) {
        return axios.put(`${USER_API_URL}/bugs/${bugId}/fixes/${id}`, item);
  	}
  	
  	deleteItem(bugId, id) {
	    console.log('delete fix object detected')
	    return axios.delete(`${USER_API_URL}/bugs/${bugId}/fixes/${id}`);
	}
	
	retrieveItem(bugId, id) {
    	return axios.get(`${USER_API_URL}/bugs/${bugId}/fixes/${id}`);
    }
    
    onSubmit(values) {
	    console.log(values);
	}
	
} export default new FixDataService()
