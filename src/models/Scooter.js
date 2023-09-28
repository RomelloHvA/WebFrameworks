/** this class is used to create a scooter object
 * 
 * @param {number} id - the id of the scooter
 * @param {string} tag - the tag of the scooter max 8 characters
 * @param {string} status - the status of the scooter can Be IDLE, IN_USE and MAINTENANCE
 * @param {object} gpsLocation - the gps location of the scooter storing longitude and latitude
 * @param {number} mileage - the mileage of the scooter in km
 * @param {number} batterCharge - the battery charge of the scooter in %
 * 
 * 
 * @returns {object} - a scooter object
 * @author Marco de Boer
 */


export class Scooter {
    id;
    tag;
    status;
    gpsLocation;
    mileage;
    batteryCharge;


   static Status = {
        IDLE: "IDLE",
        IN_USE: "IN_USE",
        MAINTENANCE: "MAINTENANCE"
    }
    
    constructor(id, tag, status, gpsLocation, mileage, batteryCharge) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }

    static async createSampleScooter(pId = 0){
        let id = pId;
        let tag = createRandomTag(8);
        let status = createRandomStatus();
        let gpsLocation = createRandomGPSLocation();
        let mileage = Math.floor(Math.random() * 10000);
        let batteryCharge = Math.floor(Math.random() * 95) + 5;
        
        return new Scooter(id, tag, status, gpsLocation, mileage, batteryCharge);
    }

    static cloneScooter(scooter){
       if (scooter === null || scooter === undefined){
           return null;
       }
       return Object.assign(new Scooter(0), scooter);
    }
    
}

/** This creates a random status and is used to create a sample scooter
 * 
 * @author Marco de Boer
 * @returns {string} - a random status from the Scooter.Status object
 */

function createRandomStatus(){
    let statusArray = [Scooter.Status.IDLE, Scooter.Status.IN_USE, Scooter.Status.MAINTENANCE];
    let randomIndex = Math.floor(Math.random() * 3);
    return statusArray[randomIndex];
}

/**
 * This creates a random gps location near Amsterdam and is used to create a sample scooter
 * 
 * @author Marco de Boer
 * @returns {object} - a gps location object with a random latitude and longitude near Amsterdam
 */
function createRandomGPSLocation(){
    let latitude = Math.round((Math.random() * (0.08) + 52.3000)* 10000) / 10000;
    let longitude = Math.round((Math.random() * (0.2) + 4.8) * 10000) / 10000;
    return {latitude: latitude, longitude: longitude};
}

/** This creates a random string with the given length and is used to create a sample scooter
 * 
 * @author From stackoverflow user csharptest.net
 * @param {number} length - the length of the random string
 * @returns {string} - a random string with the given length
 */
function createRandomTag(length) {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;
    let counter = 0;
    while (counter < length) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
      counter += 1;
    }
    return result;
}