package com.example.social.Utils.PerspectiveUtils;

public class AnalyzeCommentRequest {
    private Comment comment;
    private RequestedAttributes requestedAttributes;

    public AnalyzeCommentRequest(Comment comment, RequestedAttributes requestedAttributes) {
        this.comment = comment;
        this.requestedAttributes = requestedAttributes;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public RequestedAttributes getRequestedAttributes() {
        return requestedAttributes;
    }

    public void setRequestedAttributes(RequestedAttributes requestedAttributes) {
        this.requestedAttributes = requestedAttributes;
    }

    public static class Comment {
        private String text;

        public Comment(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class RequestedAttributes {
        private Toxicity toxicity;

        public RequestedAttributes(Toxicity toxicity) {
            this.toxicity = toxicity;
        }

        public Toxicity getToxicity() {
            return toxicity;
        }

        public void setToxicity(Toxicity toxicity) {
            this.toxicity = toxicity;
        }

        public static class Toxicity {
            private double threshold;

            public Toxicity(double threshold) {
                this.threshold = threshold;
            }

            public double getThreshold() {
                return threshold;
            }

            public void setThreshold(double threshold) {
                this.threshold = threshold;
            }
        }
    }
}
