/**
 * Copyright (C) 2007.
 * Centre for Research on Inner City Health, St. Michael's Hospital, Toronto, Ontario, Canada.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.oscarehr.common.service;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;

import org.oscarehr.casemgmt.dao.IssueDAO;
import org.oscarehr.casemgmt.model.Issue;
import org.oscarehr.common.dao.PopulationReportDAO;
import org.oscarehr.common.model.Mortalities;
import org.oscarehr.common.model.ReportStatistic;
import org.oscarehr.common.model.ShelterPopulation;
import org.oscarehr.common.model.ShelterUsage;

public class PopulationReportManager {

    private static final int ONE_YEAR = 1;
    private static final int FOUR_YEARS = 4;

    private PopulationReportDAO populationReportDAO;
    private IssueDAO issueDAO;

    public void setPopulationReportDAO(PopulationReportDAO populationReportDAO) {
        this.populationReportDAO = populationReportDAO;
    }

    public void setIssueDAO(IssueDAO issueDAO) {
        this.issueDAO = issueDAO;
    }

    public ShelterPopulation getShelterPopulation() {
        int pastYear = populationReportDAO.getCurrentAndHistoricalPopulationSize(ONE_YEAR);
        int current = populationReportDAO.getCurrentPopulationSize();

        return new ShelterPopulation(pastYear, current);
    }

    public ShelterUsage getShelterUsage() {
        int[] usages = populationReportDAO.getUsages(FOUR_YEARS);

        return new ShelterUsage(usages[PopulationReportDAO.LOW], usages[PopulationReportDAO.MEDIUM], usages[PopulationReportDAO.HIGH]);
    }

    public Mortalities getMortalities() {
        int count = populationReportDAO.getMortalities(ONE_YEAR);
        int size = populationReportDAO.getCurrentAndHistoricalPopulationSize(ONE_YEAR);

        return new Mortalities(count, size);
    }

    public Map getMajorMedicalConditions() {
        Map prevalences = new LinkedHashMap();

        int populationSize = populationReportDAO.getCurrentPopulationSize();
        Set s = PopulationReportCodes.getMajorMedicalConditions().entrySet();
        Iterator it = s.iterator();
        while(it.hasNext()){
        	Entry e = (Entry)it.next();
        
        //for (Entry e : PopulationReportCodes.getMajorMedicalConditions().entrySet()) {
            prevalences.put(e.getKey(), new ReportStatistic(populationReportDAO.getPrevalence(e.getValue()), populationSize));
        }

        return prevalences;
    }

    public Map getMajorMentalIllnesses() {
        Map prevalences = new LinkedHashMap();

        int populationSize = populationReportDAO.getCurrentPopulationSize();
        Set s = PopulationReportCodes.getMajorMentalIllness().entrySet();
        Iterator it = s.iterator();
        while(it.hasNext()){
        	Entry e = (Entry)it.next();
        //for (Entry e : PopulationReportCodes.getMajorMentalIllness().entrySet()) {
            prevalences.put(e.getKey(), new ReportStatistic(populationReportDAO.getPrevalence(e.getValue()), populationSize));
        }

        return prevalences;
    }

    public Map getSeriousMedicalConditions() {
        Map incidences = new LinkedHashMap();

        int populationSize = populationReportDAO.getCurrentPopulationSize();

        for (Entry e : PopulationReportCodes.getSeriousMedicalConditions().entrySet()) {
            incidences.put(e.getKey(), new ReportStatistic(populationReportDAO.getIncidence(e.getValue()), populationSize));
        }

        return incidences;
    }

    public Map getCategoryCodeDescriptions() {
        Map categoryCodeDescription = new LinkedHashMap();

        for (Entry e : PopulationReportCodes.getAllCodes().entrySet()) {
            String category = e.getKey();
            SortedSet codes = e.getValue();

            Map codeDescriptions = new LinkedHashMap();

            for (String code : codes) {
                Issue issue = issueDAO.findIssueByCode(code);
                String description = issue != null?issue.getDescription():"N/A";

                codeDescriptions.put(code, description);
            }

            categoryCodeDescription.put(category, codeDescriptions);
        }

        return categoryCodeDescription;
    }

}
