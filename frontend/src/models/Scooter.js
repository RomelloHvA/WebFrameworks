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
        MAINTENANCE: "MAINTENANCE",
       UNAVAILABLE: "UNAVAILABLE"
    }

    constructor(id, tag, status, gpsLocation = {latitude: 0, longitude: 0}, mileage, batteryCharge) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }

    static async createSampleScooter(pId = 0){
        let id = pId;
        let tag = await this.#createRandomTag(8);
        let status = await this.#createRandomStatus();
        let gpsLocation = await this.#createRandomGPSLocation();
        let mileage = Math.floor(Math.random() * 10000);
        let batteryCharge = Math.floor(Math.random() * 95) + 5;

        return new Scooter(id, tag, status, gpsLocation, mileage, batteryCharge);
    }

    /**
     *
     * @param scooter the scooter to be cloned.
     * @returns null if there is no given scooter or if it is undefined. Otherwise returns a cloned scooter
     * without the ID. To be used for editing a scooter.
     * @author Romello ten Broeke
     */

    static async cloneScooter(scooter) {
        if (scooter === null || scooter === undefined) {
            return null;
        }

        const clonedScooter = new Scooter(0);

        // Copy the 'gpsLocation' properties
        if (scooter.gpsLocation) {
            clonedScooter.gpsLocation = {
                latitude: scooter.gpsLocation.latitude,
                longitude: scooter.gpsLocation.longitude
            };
        }

        // Copy other properties using Object.assign
        Object.assign(clonedScooter, scooter);

        return clonedScooter;
    }
    /**
     * This method checks all the values if they have been edited. Except for the id.
     * The id is not given in the cloned scooter. The gpslocation has to be checked seperately as it is an object.
     * It is parsed because after editing it becomes a String.
     * @returns if the values have been edited.
     * @author Romello ten Broeke
     */
    equals(otherScooter){

        for (const key of Object.keys(this).filter(key => key !== 'id')) {
            if (key === 'gpsLocation') {
                if (
                    (!otherScooter.gpsLocation ||
                        parseFloat(otherScooter.gpsLocation.latitude) !== this.gpsLocation.latitude) ||
                        parseFloat(otherScooter.gpsLocation.longitude) !== this.gpsLocation.longitude)
                {
                    return false;
                }
            } else if (otherScooter[key] !== this[key]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function is used to create a scooter from a JSON object
     * @param {*} scooterFromJSON in fromat of a JSON object
     * @author Marco de Boer
     * @returns 
     */

    static copyConstructor(scooterFromJSON){
        let id = scooterFromJSON.id;
        let tag = scooterFromJSON.tag;
        let status = scooterFromJSON.status;
        let gpsLocation = scooterFromJSON.gpsLocation;
        let mileage = scooterFromJSON.mileage;
        let batteryCharge = scooterFromJSON.batteryCharge;

        return new Scooter(id, tag, status, gpsLocation, mileage, batteryCharge);
    }
    /** This creates a random status and is used to create a sample scooter
 *
 * @author Marco de Boer
 * @returns {string} - a random status from the Scooter.Status object
 */

    static async #createRandomStatus(){
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
    static async  #createRandomGPSLocation(){
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
    static async  #createRandomTag(length) {
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

}

