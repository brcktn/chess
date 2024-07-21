package handler;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import service.DataService;

public class ClearHandler {
    private final DataAccess dataAccess;

    public ClearHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void clear() throws DataAccessException {
        new DataService(dataAccess).clear();
    }
}
