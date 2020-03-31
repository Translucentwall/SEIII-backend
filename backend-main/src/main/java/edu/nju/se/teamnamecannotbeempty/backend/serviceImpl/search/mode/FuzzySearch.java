package edu.nju.se.teamnamecannotbeempty.backend.serviceImpl.search.mode;

import edu.nju.se.teamnamecannotbeempty.backend.service.search.SearchMode;
import edu.nju.se.teamnamecannotbeempty.data.domain.Paper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.highlight.Highlighter;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.SimpleQueryStringMatchingContext;
import org.hibernate.search.query.dsl.TermMatchingContext;
import org.springframework.stereotype.Component;

@Component("All")
public class FuzzySearch extends SearchMode {
    @Override
    public TermMatchingContext getFieldsBaseOnKeyword(QueryBuilder queryBuilder) {
        return queryBuilder.keyword()
                .onField(Paper.getFieldName_searchYear()).boostedTo(4f)
                .andField(Paper.getFieldName_title())
                .andField(Paper.getFieldName_author())
                .andField(Paper.getFieldName_affiliation())
                .andField(Paper.getFieldName_conference())
                .andField(Paper.getFieldName_authorKeywords());
    }

    @Override
    public SimpleQueryStringMatchingContext getFieldsBaseOnSQS(QueryBuilder queryBuilder) {
        return queryBuilder.simpleQueryString().onFields(
                Paper.getFieldName_title(),
                Paper.getFieldName_author(),
                Paper.getFieldName_affiliation(),
                Paper.getFieldName_conference(),
                Paper.getFieldName_authorKeywords(), Paper.getFieldName_searchYear()
        );
    }

    @Override
    public void highlight(Highlighter highlighter, Analyzer analyzer, Paper paper) {
        highlightTitle(paper, highlighter, analyzer);
        highlightAuthor(paper, highlighter, analyzer);
        highlightAffiliation(paper, highlighter, analyzer);
        highlightConference(paper, highlighter, analyzer);
        highlightKeyword(paper, highlighter, analyzer);
        highlightYear(paper, highlighter, analyzer);
    }
}