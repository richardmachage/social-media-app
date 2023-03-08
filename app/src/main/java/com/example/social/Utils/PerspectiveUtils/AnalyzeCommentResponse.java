package com.example.social.Utils.PerspectiveUtils;

public class AnalyzeCommentResponse {
    private AttributeScores attributeScores;

    public AttributeScores getAttributeScores() {
        return attributeScores;
    }

    public void setAttributeScores(AttributeScores attributeScores) {
        this.attributeScores = attributeScores;
    }

    public static class AttributeScores {
        private Toxicity toxicity;

        public Toxicity getToxicity() {
            return toxicity;
        }

        public void setToxicity(Toxicity toxicity) {
            this.toxicity = toxicity;
        }

        public static class Toxicity {
            private double summaryScore;

            public double getSummaryScore() {
                return summaryScore;
            }

            public void setSummaryScore(double summaryScore) {
                this.summaryScore = summaryScore;
            }
        }
    }
}
