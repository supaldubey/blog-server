const cs = {
    backend: 'http://localhost:8080',
    restActions: {
        onSuccess: (data) => {},
        onError: (err) => {}
    },
    url: (target) => `${cs.backend}/${target}`,
    navigate: (target) => window.location = `${window.location.origin}/${target}`,
    RestCalls: {
        save: (targetURL, payload, actions, errorContainerId) => {
            if(!actions) actions = cs.restActions;
            const body = payload ? JSON.stringify(payload) : null;
            $.ajax({
                type: 'POST',
                url: cs.url(targetURL),
                contentType: "application/json",
                data: body,
                success: (data) => {
                    if(actions.onSuccess) actions.onSuccess(data);
                },
                error: (err) => {
                    if(actions.onError) actions.onError(err);
                    if(errorContainerId) {
                        const errorContainer = $('#' + errorContainerId);
                        switch (err.status) {
                            case 400: {
                                const errorsArr = cs.errorHelper(err);
                                let html = '';
                                errorContainer.html(html);
                                errorsArr.forEach(err => html = html.concat('<p class="cs-ad-form-err">&#10006; ',err, '</p>'));
                                errorContainer.html(html);
                                break;
                            };
                            default: errorContainer.html('<p>'.concat(err.responseText).concat('</p>'));
                        }

                    }
                }
            });
        }
    },
    errorHelper: (err) => err['responseJSON']['parameterViolations'].map(o => o['message'])
};