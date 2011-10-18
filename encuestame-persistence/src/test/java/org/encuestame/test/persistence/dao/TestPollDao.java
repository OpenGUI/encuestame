/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.config.AbstractBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test Poll Dao.
 * @author Morales,Diana Paola paolaATencuestame.org
 * @since  March 16, 2009
 * @version $Id: $
 */
public class TestPollDao extends AbstractBase {


    /** {@link IPoll} **/
    @Autowired
    IPoll  pollI;

    /** {@link Poll} **/
    Poll poll;

    /** {@link Account}.**/
    Account user;

    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /** {@link Question} **/
    private Question question;

    /** {@link PollFolder} **/
    private PollFolder pollFolder;

    /** Max Results**/
    private Integer MAX_RESULTS = 10;

    /** **/
    private Integer START = 0;

    private Calendar myDate = Calendar.getInstance();

     /** Before.
     * @throws EnMeNoResultsFoundException
     **/
    @Before
    public void initService() throws EnMeNoResultsFoundException{
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.user);
        this.question = createQuestion("Why the roses are red?", "html");
        this.poll = createPoll(myDate.getTime(), this.question, "FDK125", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        this.pollFolder = createPollFolder("My First Poll Folder", this.userAccount);
        addPollToFolder(this.pollFolder.getId(), this.userAccount, this.poll.getPollId());
        //System.out.println("Poll created at ----> " + this.poll.getCreatedAt());
    }

     /** Test retrievePollsByUserId. **/
    @Test
    public void testFindAllPollByUserId(){
        final List<Poll> pollList = getPollDao().findAllPollByEditorOwner(this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 1, pollList.size());
    }

    /**
     * Test retrieve Poll By Id.
    **/
    @Test
    public void testGetPollById(){
        final Poll getpoll = getPollDao().getPollById(this.poll.getPollId());
        assertNotNull(getpoll);
    }

    /**
    * Test retrieve Results Poll By PollId.
    **/
    @Test
    public void testRetrievePollResultsById(){
        final Question quest = createQuestion("Do you like futboll", "Yes/No");
        final QuestionAnswer qansw = createQuestionAnswer("Yes", quest, "2020");
        createQuestionAnswer("No", quest, "2020");
        createPollResults(qansw, this.poll);
        createPollResults(qansw, this.poll);
        final List<Object[]> polli = getPollDao().retrieveResultPolls(this.poll.getPollId(), qansw.getQuestionAnswerId());
        final Iterator<Object[]> iterator = polli.iterator();
        while (iterator.hasNext()) {
            final Object[] objects = iterator.next();
         }
        assertEquals("Should be equals", 1, polli.size());
    }

    /**
     * Test Get Poll Folder by User.
     */
    @Test
    public void testGetPollFolderByIdandUser(){
        assertNotNull(pollFolder);
        final PollFolder pfolder = getPollDao().getPollFolderByIdandUser(this.pollFolder.getId(), this.userAccount);
        assertEquals("Should be equals", this.pollFolder.getId(), pfolder.getId());
     }

    /**
     * Test Get Poll By User
     */
    @Test
    public void testGetPollByIdandUserId(){
        assertNotNull(this.poll);
        final Poll poll = getPollDao().getPollById(this.poll.getPollId(), this.userAccount);
        assertNotNull(poll);
        assertEquals("Should be equals", this.poll.getPollId(), poll.getPollId());
    }

    /**
     * Test Get Polls By Question Keyword.
     */
    @Test
    public void testGetPollsByQuestionKeyword(){
        assertNotNull(this.poll);
        Question question1 = createQuestion("Is Guns and Roses your favorite Group?", "html");
        createPoll(myDate.getTime(), question1, "FDK125sada", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        Question question2 = createQuestion("Real Madrid VS Barcelona", "html");
        createPoll(myDate.getTime(), question2, "FDK125231321", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        final String keywordQuestion = "roses";
        flushIndexes();
        final List<Poll> listPoll = getPollDao().getPollsByQuestionKeyword(keywordQuestion, this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 2, listPoll.size());
    }

    /**
     * Test getPollFolderById.
     */
    @Test
    public void testGetPollFolderById(){
        assertNotNull(this.pollFolder);
        final PollFolder pollFolder = getPollDao().getPollFolderById(this.pollFolder.getId());
        assertEquals("Should be equals", this.pollFolder.getId(), pollFolder.getId());
    }

    /**
     * Test Get Polls By PollFolderId.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetPollsByPollFolderId() throws EnMeNoResultsFoundException{
         assertNotNull(this.pollFolder);
         assertNotNull(poll);
         final Poll addPoll = addPollToFolder(this.pollFolder.getId(), this.userAccount, this.poll.getPollId());
         assertNotNull(addPoll);
         final List<Poll> pfolder = getPollDao().getPollsByPollFolderId(this.userAccount, this.pollFolder);
         assertEquals("Should be equals", 1, pfolder.size());
    }

    /**
     * Test Get Polls by creation date.
     */
    @Test
    public void testGetPollByUserIdDate(){
        final Calendar yesterdayDate = Calendar.getInstance();
        yesterdayDate.add(Calendar.DAY_OF_WEEK, -1);
        final Date yesterday = yesterdayDate.getTime();
        // Second Poll
        createPoll(new Date(), this.question, "FDK115", this.userAccount,
                Boolean.TRUE, Boolean.TRUE);
        // Third Poll
        createPoll(yesterday, this.question, "FDK195", this.userAccount,
                Boolean.TRUE, Boolean.TRUE);
        Assert.assertNotNull(this.poll);
        final List<Poll> pollList = getPollDao().getPollByUserIdDate(yesterday,
                this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 3, pollList.size());
    }

    @Test
    public void testRetrievePollsByUserId(){
        final Question question2 =  createQuestion("Why the sea is blue?","html");
        createPoll(new Date(), question2, "FDK126", this.userAccount, Boolean.TRUE, Boolean.TRUE);
        final List<Poll> pollbyUser = getPollDao().retrievePollsByUserId(this.userAccount, this.MAX_RESULTS, this.START);
        assertEquals("Should be equals", 2, pollbyUser.size());
    }

    @Test
    public void testGetPollFolderBySecUser(){
        createPollFolder("My Second Poll Folder", this.userAccount);
        createPollFolder("My Third Poll Folder", this.userAccount);
        final List<PollFolder> pollFolderbyUser = getPollDao().getPollFolderByUserAccount(this.userAccount);
        assertEquals("Should be equals", 3, pollFolderbyUser.size());
    }

    @Test
    public void testPollsByPollFolder(){
        final List<Poll> pollsbyFolder = getPollDao().getPollsByPollFolder(this.userAccount, this.pollFolder);
        assertEquals("Should be equals", 1, pollsbyFolder.size());
    }
}
