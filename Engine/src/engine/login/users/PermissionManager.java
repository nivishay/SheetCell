package engine.login.users;

import dto.permissions.PermissionLine;
import dto.permissions.PermissionStatus;
import dto.permissions.RequestPermission;
import dto.permissions.RequestStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionManager {

    private final Map<String, List<PermissionLine>> currentSheetNameToPermissionLines = new HashMap<>();
    private final Map<String, List<PermissionLine>> allHistorySheetNameToPermissionLines = new HashMap<>();
    private final UserManager userManager;
    private final Map<String, List<RequestPermission>> userNameToHisRequestList = new HashMap<>();
    private final Map<String, List<ResponsePermission>> ownerNameToHisResponseList = new HashMap<>();


    public PermissionManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public synchronized void addPermission(String sheetName, String userName,
                                           PermissionStatus status, RequestStatus requestStatus) {

        PermissionLine permissionLine = new PermissionLine(userName, status, requestStatus);
        PermissionLine permissionLineForAllHistory = new PermissionLine(userName, status, requestStatus);

        // Initialize lists if they don't exist
        allHistorySheetNameToPermissionLines.computeIfAbsent(sheetName, k -> new ArrayList<>());
        currentSheetNameToPermissionLines.computeIfAbsent(sheetName, k -> new ArrayList<>());

        // Add to the history, always appending
        allHistorySheetNameToPermissionLines.get(sheetName).add(permissionLineForAllHistory);

        // Update the current permission, removing any previous permission for the same user
        List<PermissionLine> currentPermissions = currentSheetNameToPermissionLines.get(sheetName);

        if(permissionLine.isApprovedByOwner()){
            currentPermissions.removeIf(perm -> perm.getUserName().equals(userName)); // Remove old permission
            currentPermissions.add(permissionLine); // Add the new permission
        }
    }

    public synchronized void removePermission(String sheetName, String userName) {
        List<PermissionLine> permissionLines = currentSheetNameToPermissionLines.get(sheetName);
        List<PermissionLine> allHistoryPermissionLines = allHistorySheetNameToPermissionLines.get(sheetName);
        if (permissionLines != null) {
            permissionLines.removeIf(permissionLine -> permissionLine.getUserName().equals(userName));
            allHistoryPermissionLines.removeIf(permissionLine -> permissionLine.getUserName().equals(userName));
        }
    }

    public List<PermissionLine> getPermissionStatusOfSheet(String sheetName) {
        return allHistorySheetNameToPermissionLines.get(sheetName);
    }

    public Map<String, List<PermissionLine>> getCurrentSheetNameToPermissionLines() {
        return currentSheetNameToPermissionLines;
    }

    public synchronized void removeUser(String userName) {
        for (String sheetName : currentSheetNameToPermissionLines.keySet()) {
            removePermission(sheetName, userName);
        }
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public synchronized void addRequestPermission(String sheetName, String userName, PermissionStatus permissionStatus) {
        // Find the owner of the sheet
        String ownerName = findOwner(sheetName);

        if (ownerName == null) {
            throw new IllegalStateException("Owner not found for sheet: " + sheetName);
        }

        // Create the request permission and response permission objects
        RequestPermission requestPermission = new RequestPermission(sheetName, ownerName, permissionStatus);
        ResponsePermission responsePermission = new ResponsePermission(sheetName, userName, permissionStatus);

        // Ensure that userName has a list in userNameToRequestPermission
        userNameToHisRequestList.computeIfAbsent(userName, k -> new ArrayList<>()).add(requestPermission);

        // Ensure that ownerName has a list in userNameToResponsePermission
        ownerNameToHisResponseList.computeIfAbsent(ownerName, k -> new ArrayList<>()).add(responsePermission);

        PermissionLine newPermissionLine = new PermissionLine(userName, permissionStatus, RequestStatus.PENDING);
        allHistorySheetNameToPermissionLines.get(sheetName).add(newPermissionLine);
    }

    private String findOwner(String sheetName) {
        for (PermissionLine permissionLine : currentSheetNameToPermissionLines.get(sheetName)) {
            if (permissionLine.getPermissionStatus() == PermissionStatus.OWNER) {
                return permissionLine.getUserName();
            }
        }
        return null;
    }

    public List<RequestPermission> getUserRequestPermission(String userName) {
        return userNameToHisRequestList.get(userName);
    }

    public List<ResponsePermission> getUserResponsePermission(String userName) {
        return ownerNameToHisResponseList.get(userName);
    }

    public void updateOwnerResponseForRequest(String ownerName, String sheetName, String userName,
                                              PermissionStatus permissionStatus, RequestStatus requestStatus) {

        List<ResponsePermission> myResponses = ownerNameToHisResponseList.get(ownerName);
        myResponses.forEach(responsePermission -> {
            if (responsePermission.getSheetNameForRequest().equals(sheetName) &&
                    responsePermission.getUserNameForRequest().equals(userName)) {

                responsePermission.setWasAnswered(true);
            }
        });

        List<RequestPermission> myRequests = userNameToHisRequestList.get(userName);
        myRequests.forEach(requestPermission -> {
            if (requestPermission.getSheetNameForRequest().equals(sheetName) &&
                    requestPermission.getUserNameForRequest().equals(ownerName)) {

                requestPermission.setWasAnswered(true);
            }
        });

        List<PermissionLine> permissionLines = currentSheetNameToPermissionLines.get(sheetName);
        if (permissionLines != null) {
            boolean found = false;

            for (PermissionLine permissionLine : permissionLines) {
                if (permissionLine.getUserName().equals(userName)) {
                    if(requestStatus == RequestStatus.APPROVED){
                        permissionLine.setPermissionStatus(permissionStatus);
                        permissionLine.setRequestStatus(requestStatus);
                        found = true;
                        break;
                    }
                }
            }

            PermissionLine newPermissionLine = new PermissionLine(userName, permissionStatus, requestStatus);
            PermissionLine newPermissionLineForAllHistory = new PermissionLine(userName, permissionStatus, requestStatus);

            if (!found && (requestStatus == RequestStatus.APPROVED)) {
                permissionLines.add(newPermissionLine);
            }


            boolean foundInAllHistory = false;

            List<PermissionLine> allHistoryPermissionLines = allHistorySheetNameToPermissionLines.get(sheetName);
            for(PermissionLine permissionLine : allHistoryPermissionLines){
                if(permissionLine.getUserName().equals(userName) && permissionLine.getRequestStatus() == RequestStatus.PENDING){
                    permissionLine.setPermissionStatus(permissionStatus);
                    permissionLine.setRequestStatus(requestStatus);
                    foundInAllHistory = true;
                }
            }

            if(!foundInAllHistory){
                allHistorySheetNameToPermissionLines.get(sheetName).add(newPermissionLineForAllHistory);
            }
        }
    }

    public PermissionStatus getPermission(String sheetName, String userName) {

        List<PermissionLine> permissionLines = currentSheetNameToPermissionLines.get(sheetName);
        if (permissionLines != null) {
            for (PermissionLine permissionLine : permissionLines) {
                if (permissionLine.getUserName().equals(userName)) {
                    return permissionLine.getPermissionStatus();
                }
            }
        }

        return PermissionStatus.NONE;
    }
}