---
layout: article
hideNewsletter: true
header:
  image: sample
  text: Subscribe to the newsletter
---
<div id="mc_embed_signup" class="newsletter-submission-page">
    <form action="https://guigarage.us4.list-manage.com/subscribe/post?u=7eb4cd61ed09b0d527f015e21&amp;id=6c968c32d2" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" class="validate" target="_blank" novalidate>
        <div id="mc_embed_signup_scroll">
            <h2>Subscribe to our newsletter</h2>            
            <div class="field">
                <label class="label">Email Address</label>
                <div class="control has-icons-left">
                    <input type="email" value="" name="EMAIL" class="input required email" id="mce-EMAIL">
                    <span class="icon is-small is-left">
                        <i class="fas fa-envelope"></i>
                    </span>
                  </div>
                  <p class="help is-info">Required field</p>
            </div>
            <div class="field">
                <label class="label">First Name</label>
                <div class="control">
                    <input type="text" value="" name="FNAME" class="input" id="mce-FNAME">
                </div>
            </div>
            <div class="field">
                <label class="label">Last Name</label>
                <div class="control">
                    <input type="text" value="" name="LNAME" class="input" id="mce-LNAME">
                </div>
            </div>
            
            <div class="field">
                <p>Please select all the ways you would like to hear from Guigarage:</p>
                <div class="control">
                    <label class="checkbox">
                        <input type="checkbox" id="gdpr_47908" name="gdpr[47908]" value="Y" class="checkbox av-checkbox">
                        Email
                    </label>
                    <p id="email-required-field" class="help is-info">Required field</p>
                </div>
            </div>

            <div class="field">
                <p>You can unsubscribe at any time by clicking the link in the footer of our emails. I will collect, use and protect your data in accordance with my <a href="/pages/privacy" target="_blank">Privacy Policy.</a></p>
                <p>We use Mailchimp as our marketing platform. By clicking below to subscribe, you acknowledge that your information will be transferred to Mailchimp for processing. Learn more about Mailchimp's privacy practices <a href="https://mailchimp.com/legal/" target="_blank">here.</a></p>
            </div>

            <div class="field">
                <div class="control">
                    <input disabled type="submit" value="Subscribe" name="subscribe" id="mc-embedded-subscribe" class="button is-primary is-fullwidth is-medium is-outlined">
                </div>
            </div>
            <script>
                var checkbox = document.getElementById('gdpr_47908');
                var button = document.getElementById('mc-embedded-subscribe');
                var mailRequiredField = document.getElementById('email-required-field');
                checkbox.addEventListener('change', function() {
                    if(checkbox.checked) {
                        button.removeAttribute('disabled');
                        mailRequiredField.style.visibility = 'hidden';
                    } else {
                        button.setAttribute('disabled', 'true');
                        mailRequiredField.style.visibility = 'visible';
                    }
                });
            </script>

            <div class="field">
                <div class="response" id="mce-error-response" style="display:none"></div>
                <div class="response" id="mce-success-response" style="display:none"></div>
            </div>
            
            <div id="mce-responses" class="clear">
            </div>    <!-- real people should not fill this in and expect good things - do not remove this or risk form bot signups-->
            <div style="position: absolute; left: -5000px;" aria-hidden="true"><input type="text" name="b_7eb4cd61ed09b0d527f015e21_6c968c32d2" tabindex="-1" value=""></div>
        </div>
    </form>
</div>